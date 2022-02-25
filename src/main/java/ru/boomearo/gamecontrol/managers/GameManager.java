package ru.boomearo.gamecontrol.managers;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.fastasyncworldedit.core.extent.processor.lighting.RelightMode;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.exceptions.GameControlException;
import ru.boomearo.gamecontrol.exceptions.PlayerGameException;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.StoredRegenArena;
import ru.boomearo.gamecontrol.objects.StoredRegenGame;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.arena.ClipboardRegenableGameArena;
import ru.boomearo.gamecontrol.objects.defactions.GameControlDefaultAction;
import ru.boomearo.gamecontrol.objects.defactions.IDefaultAction;
import ru.boomearo.gamecontrol.objects.states.game.IGameState;
import ru.boomearo.gamecontrol.objects.states.game.IRegenState;
import ru.boomearo.gamecontrol.objects.statistics.IStatisticsManager;
import ru.boomearo.serverutils.utils.other.ExtendedThreadFactory;

/**
 * Главный менеджер всех игр в плагине. Только через него можно зарегистрировать игру или добавлять игроков в любые другие игры.
 */
public final class GameManager {

    private final ConcurrentMap<Class<? extends JavaPlugin>, IGameManager<? extends IGamePlayer>> gamesClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, IGameManager<? extends IGamePlayer>> gamesNames = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, IGamePlayer> players = new ConcurrentHashMap<>();

    private ThreadPoolExecutor regenPool = null;
    private ConcurrentMap<String, StoredRegenGame> regenData = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Clipboard> cachedClipboards = new ConcurrentHashMap<>();

    private ScheduledThreadPoolExecutor savePool = null;

    private IDefaultAction defaultAction = new GameControlDefaultAction();

    private final Object lock = new Object();

    public static final String prefix = "§8[§9GameControl§8]: §7";

    public static final ChatColor backgroundTextColor = ChatColor.of(new Color(215, 215, 215));

    public IDefaultAction getDefaultAction() {
        return this.defaultAction;
    }

    /**
     * Добавить действие по умолчанию
     * @throws ConsoleGameException если действие null
     */
    public void setDefaultAction(IDefaultAction action) throws ConsoleGameException {
        if (action == null) {
            throw new ConsoleGameException("Действие по умолчанию не может быть нулем!");
        }

        this.defaultAction = action;
    }

    public void initRegenPool() {
        if (this.regenPool == null) {
            this.regenPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, new ExtendedThreadFactory("ArenaRegen", 3));
        }
    }

    public void stopRegenPool() {
        if (this.regenPool != null) {
            this.regenPool.shutdown();
        }
    }

    public void initSavePool() {
        if (this.savePool == null) {
            this.savePool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1, new ExtendedThreadFactory("SaveData", 3));

            loadScheduledSaveTasks();
        }
    }

    private void loadScheduledSaveTasks() {
        //Инициализируем шедулер с задачей, которая сохраняет статистику от всех игр
        this.savePool.scheduleAtFixedRate(this::saveAllGameStats, 300, 300, TimeUnit.SECONDS);
    }

    public void saveAllGameStats() {
        try {
            for (IGameManager<? extends IGamePlayer> igm : this.gamesClasses.values()) {
                IStatisticsManager statisticsManager = igm.getStatisticManager();
                if (statisticsManager == null) {
                    continue;
                }
                statisticsManager.onSaveAllData();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopSavePool() throws InterruptedException {
        if (this.savePool != null) {
            this.savePool.shutdown();
            this.savePool.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

    /**
     * Добавить в очередь указанную арену на регенерацию
     * @throws ConsoleGameException если арена null, пул регенераций не был создан или пул регенераций выключен.
     */
    public void queueRegenArena(ClipboardRegenableGameArena<? extends IGamePlayer> arena) throws ConsoleGameException {
        if (arena == null) {
            throw new ConsoleGameException("Арена не может быть нулем!");
        }

        if (this.regenPool == null) {
            throw new ConsoleGameException("Пул регенераций не был инициализирован!");
        }

        if (this.regenPool.isShutdown()) {
            throw new ConsoleGameException("Пул регенераций выключился!");
        }

        this.regenPool.execute(() -> {
            String gameName = arena.getManager().getGameName();

            try {

                GameControl.getInstance().getLogger().info("Начинаю регенерацию арены '" + arena.getName() + "' в игре '" + gameName + "'");
                long start = System.currentTimeMillis();

                String schematicName = gameName.toLowerCase() + "_" + arena.getName().toLowerCase();

                File schFile = new File(GameControl.getInstance().getDataFolder(), File.separator + "schematics" + File.separator + schematicName + ".schem");

                if (!schFile.exists()) {
                    throw new ConsoleGameException("Файл арены '" + arena.getName() + "' в игре '" + gameName + "' не найден!");
                }

                if (!schFile.isFile()) {
                    throw new ConsoleGameException("Файл арены '" + arena.getName() + "' в игре '" + gameName + "' не является файлом!");
                }

                Clipboard clipBoard = this.cachedClipboards.get(schematicName);
                if (clipBoard == null) {
                    Clipboard cb;

                    ClipboardFormat format = ClipboardFormats.findByFile(schFile);

                    if (format == null) {
                        throw new ConsoleGameException("Файл схемы арены '" + arena.getName() + "' игры '" + gameName + "' не найден!");
                    }

                    try (ClipboardReader reader = format.getReader(new FileInputStream(schFile))) {
                        cb = reader.read();
                    }

                    if (cb == null) {
                        throw new ConsoleGameException("Схема арены '" + arena.getName() + "' игры '" + gameName + "' является нулем!");
                    }

                    cb.flush();

                    this.cachedClipboards.put(schematicName, cb);

                    clipBoard = cb;
                }

                Location loc = arena.getOriginCenter();

                if (loc == null) {
                    throw new ConsoleGameException("Центральная точка схемы арены '" + arena.getName() + "' игры '" + gameName + "' является нулем!");
                }

                World w = BukkitAdapter.adapt(arena.getWorld());

                if (w == null) {
                    throw new ConsoleGameException("Мир арены '" + arena.getName() + "' игры '" + gameName + "' является нулем!");
                }

                //Если при операции появляется исключение, прерываем выполнение всего
                try (EditSession es = WorldEdit.getInstance().newEditSessionBuilder()
                        .world(w)
                        .fastMode(true)
                        .relightMode(RelightMode.NONE)
                        .build()) {
                    Operation op = new ClipboardHolder(clipBoard)
                            .createPaste(es)
                            .to(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()))
                            .ignoreAirBlocks(false)
                            .copyEntities(false)
                            .copyBiomes(false)
                            .build();

                    Operations.complete(op);
                }

                long end = System.currentTimeMillis();

                //Арена теперь считается восстановленной, поэтому снимаем глобальный статус о том что ее надо восстановить.
                GameControl.getInstance().getGameManager().setRegenGame(arena, false);

                GameControl.getInstance().getLogger().info("Регенерация арены '" + arena.getName() + "' в игре '" + gameName + "' успешно завершена за " + (end - start) + "мс.");
            }
            catch (Throwable e) {
                GameControl.getInstance().getLogger().warning("Произошла ошибка при регенерации арены '" + arena.getName() + "' в игре '" + gameName + "'");
                e.printStackTrace();
            }
            finally {
                //После регенерации арены, получаем ее состояние
                IGameState state = arena.getState();
                //Снимаем ожидание регенерации
                if (state instanceof IRegenState regenState) {
                    regenState.setWaitingRegen(false);
                }
            }
        });
    }

    public void queueSaveTask(Runnable task) throws ConsoleGameException {
        if (task == null) {
            throw new ConsoleGameException("Задача не может быть нулем!");
        }

        if (this.savePool == null) {
            throw new ConsoleGameException("Пул сохранений не был инициализирован!");
        }

        if (this.savePool.isShutdown()) {
            throw new ConsoleGameException("Пул сохранений выключился!");
        }

        this.savePool.execute(task);
    }

    /**
     * Зарегистрировать игру, используя главный класс плагина (JavaPlugin) и свою реализацию IGameManager.
     * Следует вызывать этот метод в своем методе onEnable().
     * Если плагин имеет арены, реализующие регенерацию, будет выполнена попытка регенерации если это требуется.
     * @throws ConsoleGameException если класс или реализация null, или игра уже зарегистрирована
     */
    public void registerGame(Class<? extends JavaPlugin> clazz, IGameManager<? extends IGamePlayer> manager) throws ConsoleGameException {
        if (clazz == null || manager == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }

        synchronized (this.lock) {
            IGameManager<? extends IGamePlayer> igm = this.gamesClasses.get(clazz);
            if (igm != null) {
                throw new ConsoleGameException("Игра " + igm.getGameName() + " уже зарегистрирована!");
            }

            this.gamesClasses.put(clazz, manager);
            this.gamesNames.put(manager.getGameName(), manager);

            //Если находим эту игру в списке игр где есть инфа о регенерации
            StoredRegenGame rg = this.regenData.get(manager.getGameName());
            if (rg != null) {
                //Получаем все арены которые были записаны в этой игре для регенераций
                for (StoredRegenArena ra : rg.getAllArenas()) {
                    //убеждаемся что арена есть, она поддерживает регенерацию и самое главное, требует ли регенерацию.
                    AbstractGameArena<? extends IGamePlayer> aga = manager.getGameArena(ra.getName());
                    if (aga == null) {
                        continue;
                    }

                    if (!(aga instanceof ClipboardRegenableGameArena<? extends IGamePlayer> crga)) {
                        continue;
                    }

                    if (!ra.isNeedRegen()) {
                        continue;
                    }

                    //Добавляем в очередь задачу на регенерацию

                    queueRegenArena(crga);
                }
            }

            //Если у игры есть статистика, то загружаем ее
            IStatisticsManager statisticsManager = manager.getStatisticManager();
            if (statisticsManager != null) {
                try {
                    GameControl.getInstance().getLogger().info("Загружаем базу данных игры " + manager.getGameName() + "..");
                    statisticsManager.onEnable();
                    GameControl.getInstance().getLogger().info("База данных игры " + manager.getGameName() + " успешно загружена.");
                }
                catch (Exception e) {
                    GameControl.getInstance().getLogger().info("Не удалось загрузить базу данных игры " + manager.getGameName() + "..");
                    e.printStackTrace();
                }
            }

            GameControl.getInstance().getLogger().info("Игра " + manager.getGameName() + " успешно зарегистрирована!");
        }
    }

    /**
     * Удалить регистрацию игры, используя главный класс плагина (JavaPlugin).
     * При удалении регистрации, пытается отключить от игры всех игроков в этой игре.
     * @throws ConsoleGameException если класс null или не был зарегистрирован
     */
    public void unregisterGame(Class<? extends JavaPlugin> clazz) throws ConsoleGameException {
        if (clazz == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }

        synchronized (this.lock) {
            IGameManager<? extends IGamePlayer> igm = this.gamesClasses.get(clazz);
            if (igm == null) {
                throw new ConsoleGameException("Класс " + clazz.getName() + " не был зарегистрирован!");
            }

            this.gamesClasses.remove(clazz);
            this.gamesNames.remove(igm.getGameName());

            //Кикаем игроков из игры
            for (IGamePlayer igma : igm.getAllPlayers()) {
                try {
                    leaveGame(igma.getPlayer());
                }
                catch (GameControlException ignored) {
                }
            }

            //Если у игры была статистика, то выключаем ее
            IStatisticsManager statisticsManager = igm.getStatisticManager();
            if (statisticsManager != null) {
                //Принудительно сохраняем сначала данные
                statisticsManager.onSaveAllData();

                //Затем выключаемся с базы данных
                try {
                    GameControl.getInstance().getLogger().info("Отключаюсь от базы данных игры " + igm.getGameName() + "..");
                    statisticsManager.onDisable();
                    GameControl.getInstance().getLogger().info("Успешное отключение от базы данных игры " + igm.getGameName() + "..");
                }
                catch (Exception e) {
                    GameControl.getInstance().getLogger().info("Не удалось отключиться от базы данных игры" + igm.getGameName() + "..");
                    e.printStackTrace();
                }
            }

            GameControl.getInstance().getLogger().info("Игра " + igm.getGameName() + " больше не зарегистрирована.");
        }
    }

    /**
     * @return игру по указанному классу плагина (JavaPlugin)
     * @see IGameManager
     */
    public IGameManager<? extends IGamePlayer> getGameByClass(Class<? extends JavaPlugin> clazz) {
        return this.gamesClasses.get(clazz);
    }

    /**
     * @return коллекцию, в которой все зарегистрированные игры
     */
    public Collection<IGameManager<? extends IGamePlayer>> getAllGameManagers() {
        return this.gamesClasses.values();
    }

    /**
     * @return коллекцию, в которой все зарегистрированные главные классы плагинов (JavaPlugin) этих игр
     */
    public Set<Class<? extends JavaPlugin>> getAllGameClasses() {
        return this.gamesClasses.keySet();
    }

    /**
     * @return игру по имени игры
     * @see IGameManager
     */
    public IGameManager<? extends IGamePlayer> getGameByName(String game) {
        return this.gamesNames.get(game);
    }

    /**
     * Добавляет игрока в арену игры по классу главного плагина который зарегистрировал эту игру.
     * Следует вызывать только этот метод для присоединения любого игрока к любой арене в любой игре.
     * @throws ConsoleGameException если один из аргументов является null или игра не зарегистрирована
     * @throws PlayerGameException если игрок уже в игре или произошла другая ошибка в методе {@link IGameManager#join(Player, String, IDefaultAction) }
     */
    public void joinGame(Player pl, Class<? extends JavaPlugin> clazz, String arena) throws ConsoleGameException, PlayerGameException {
        if (clazz == null || pl == null || arena == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }

        synchronized (this.lock) {
            IGamePlayer igp = this.players.get(pl.getName());
            if (igp != null) {
                throw new PlayerGameException("Вы уже в игре!");
            }

            IGameManager<? extends IGamePlayer> igm = this.gamesClasses.get(clazz);
            if (igm == null) {
                throw new ConsoleGameException("Игра " + clazz.getName() + " не найдена!");
            }

            //Вернет игрока если удалось войти в игру. Если войти не удалось, должно быть любое исключение этого плагина.
            IGamePlayer newIgp = igm.join(pl, arena, this.defaultAction);

            this.players.put(pl.getName(), newIgp);
        }
    }

    /**
     * Удаляет игрока из этой игры.
     * Следует вызывать только этот метод для удаления любого игрока к любой арене в любой игре.
     * @throws ConsoleGameException если аргумент null
     * @throws PlayerGameException если игрок не в игре
     */
    public void leaveGame(Player pl) throws ConsoleGameException, PlayerGameException {
        if (pl == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }

        synchronized (this.lock) {
            IGamePlayer igp = this.players.get(pl.getName());
            if (igp == null) {
                throw new PlayerGameException("Вы не в игре!");
            }

            //Сначала удаляем в общей, даже если произойдет исключение, выход из игры должен быть ВСЕГДА
            this.players.remove(pl.getName());

            //Выполняем выход самой мини игры
            igp.getArena().getManager().leave(pl, this.defaultAction);
        }
    }

    /**
     * @return игрока, который находится под контролем одной из зарегистрированных игр
     * @see IGamePlayer
     */
    public IGamePlayer getGamePlayer(String name) {
        return this.players.get(name);
    }

    /**
     * @return коллекцию, содержащую всех игроков, которые под контролем разных игр
     */
    public Collection<IGamePlayer> getAllPlayers() {
        return this.players.values();
    }


    public StoredRegenGame getRegenGameByName(String name) {
        return this.regenData.get(name);
    }

    /**
     * Сохраняет глобальное состояние регенереации арены. Используется для полного контроля регенерации.
     * Например, если игра не успела восстановить арену, и установить здесь значение false, при регистрации игры, арена будет автоматически восстановлена.
     * @param arena Арена
     * @param needRegen Требуется ли восстановить арену
     * @throws ConsoleGameException Если арена является null
     */
    public void setRegenGame(ClipboardRegenableGameArena<? extends IGamePlayer> arena, boolean needRegen) throws ConsoleGameException {
        if (arena == null) {
            throw new ConsoleGameException("Арена не может быть нулем!");
        }

        String gameName = arena.getManager().getGameName();
        StoredRegenGame rg = this.regenData.get(gameName);
        if (rg == null) {
            StoredRegenGame newRg = new StoredRegenGame(gameName, new ConcurrentHashMap<>());
            this.regenData.put(gameName, newRg);
            rg = newRg;
        }

        StoredRegenArena ra = rg.getRegenArena(arena.getName());
        if (ra == null) {
            StoredRegenArena newRa = new StoredRegenArena(arena.getName(), false);
            rg.addRegenArena(newRa);
            ra = newRa;
        }

        ra.setNeedRegen(needRegen);

        //Сохраняем это дело в другом потоке отведенным под это
        queueSaveTask(this::saveRegenData);
    }

    public void loadRegenData() {
        ConcurrentMap<String, StoredRegenGame> regenData = new ConcurrentHashMap<>();

        GameControl gc = GameControl.getInstance();
        gc.reloadConfig();

        List<StoredRegenGame> rg = (List<StoredRegenGame>) gc.getConfig().getList("regenData");
        if (rg != null) {
            for (StoredRegenGame rr : rg) {
                regenData.put(rr.getName(), rr);
            }
        }

        this.regenData = regenData;
    }

    public void saveRegenData() {
        GameControl gc = GameControl.getInstance();

        FileConfiguration fc = gc.getConfig();
        fc.set("regenData", null);

        List<StoredRegenGame> rr = new ArrayList<>(this.regenData.values());
        fc.set("regenData", rr);

        gc.saveConfig();
    }
}
