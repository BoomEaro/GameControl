package ru.boomearo.gamecontrol.managers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
import ru.boomearo.gamecontrol.objects.RegenArena;
import ru.boomearo.gamecontrol.objects.RegenGame;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.arena.ClipboardRegenableGameArena;
import ru.boomearo.gamecontrol.objects.defactions.GameControlDefaultAction;
import ru.boomearo.gamecontrol.objects.defactions.IDefaultAction;
import ru.boomearo.gamecontrol.runnable.ExtendedThreadFactory;
import ru.boomearo.gamecontrol.runnable.RegenTask;

public final class GameManager {

    private final ConcurrentMap<Class<? extends JavaPlugin>, IGameManager> gamesClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, IGameManager> gamesNames = new ConcurrentHashMap<>();
    
    private final ConcurrentMap<String, IGamePlayer> players = new ConcurrentHashMap<>();
    
    private ThreadPoolExecutor regenPool = null;
    private ConcurrentMap<String, RegenGame> regenData = new ConcurrentHashMap<>();
    
    private ThreadPoolExecutor savePool = null;
    
    private IDefaultAction defaultAction = new GameControlDefaultAction();
    
    private final Object lock = new Object();
    
    public static final String prefix = "§8[§9GameControl§8]: §7";
   
    public static final ChatColor backgroundTextColor = ChatColor.of(new Color(215, 215, 215));
    public static final ChatColor moneyColor = ChatColor.of(new Color(166, 255, 0));
    
    public IDefaultAction getDefaultAction() {
        return this.defaultAction;
    }
    
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
            this.savePool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, new ExtendedThreadFactory("SaveData", 3));
        }
    }
    
    public void stopSavePool() throws InterruptedException {
        if (this.savePool != null) {
           this.savePool.shutdown();
           this.savePool.awaitTermination(1, TimeUnit.MINUTES);
        }
    }
    
    //Добавляет в очередь реализацию runnable в которой требуется сама арены и действие после регенерации
    public void queueRegenArena(RegenTask task) throws ConsoleGameException {
        if (task == null) {
            throw new ConsoleGameException("Задача не может быть нулем!");
        }

        if (this.regenPool == null) {
            throw new ConsoleGameException("Пул регенераций не был инициализирован!");
        }
        
        if (this.regenPool.isShutdown()) {
            throw new ConsoleGameException("Пул регенераций выключился!");
        }

        this.regenPool.execute(task);
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
    
    public void registerGame(Class<? extends JavaPlugin> clazz, IGameManager manager) throws ConsoleGameException {
        if (clazz == null || manager == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }
        
        synchronized (this.lock) {
            IGameManager igm = this.gamesClasses.get(clazz);
            if (igm != null) {
                throw new ConsoleGameException("Игра " + igm.getGameName() + " уже зарегистрирована!");
            }
            
            this.gamesClasses.put(clazz, manager);
            this.gamesNames.put(manager.getGameName(), manager);
            
            //Если находим эту игру в списке игр где есть инфа о регенерации
            RegenGame rg = this.regenData.get(manager.getGameName());
            if (rg != null) {
                //Получаем все арены которые были записаны в этой игре для регенераций
                for (RegenArena ra : rg.getAllArenas()) {
                    //убеждаемся что арена есть, она поддерживает регенерацию и самое главное, требует ли регенерацию.
                    AbstractGameArena aga = manager.getGameArena(ra.getName());
                    if (aga == null) {
                        continue;
                    }
                    
                    if (!(aga instanceof ClipboardRegenableGameArena crga)) {
                        continue;
                    }
                    
                    if (!ra.isNeedRegen()) {
                        continue;
                    }
                    
                    //Добавляем в очередь задачу на регенерацию

                    queueRegenArena(new RegenTask(crga, null));
                }
            }
            
            GameControl.getInstance().getLogger().info("Игра " + manager.getGameName() + " успешно зарегистрирована!");
        }
    }
    
    public void unregisterGame(Class<? extends JavaPlugin> clazz) throws ConsoleGameException {
        if (clazz == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }
        
        synchronized (this.lock) {
            IGameManager igm = this.gamesClasses.get(clazz);
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
                catch (GameControlException ignored) {}
            }
            
            GameControl.getInstance().getLogger().info("Игра " + igm.getGameName() + " больше не зарегистрирована.");
        }
    }
    
    public IGameManager getGameByClass(Class<? extends JavaPlugin> clazz) {
        return this.gamesClasses.get(clazz);
    }
    public Collection<IGameManager> getAllGameManagers() {
        return this.gamesClasses.values();
    }
    public Set<Class<? extends JavaPlugin>> getAllGameClasses() {
        return this.gamesClasses.keySet();
    }
    
    public IGameManager getGameByName(String game) {
        return this.gamesNames.get(game);
    }
    
    public void joinGame(Player pl, Class<? extends JavaPlugin> clazz, String arena) throws ConsoleGameException, PlayerGameException {
        if (clazz == null || pl == null || arena == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }
        
        synchronized (this.lock) {
            IGamePlayer igp = this.players.get(pl.getName());
            if (igp != null) {
                throw new PlayerGameException("Вы уже в игре!");
            }
            
            IGameManager igm = this.gamesClasses.get(clazz);
            if (igm == null) {
                throw new ConsoleGameException("Игра " + clazz.getName() + " не найдена!");
            }
            
            //Готовим игрока для входа в игру
            this.defaultAction.performDefaultJoinAction(pl);
            
            //Вернет игрока если удалось войти в игру. Если войти не удалось, должно быть любое исключение этого плагина.
            IGamePlayer newIgp = igm.join(pl, arena);
            
            this.players.put(pl.getName(), newIgp);
        }
    }
    
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
            igp.getArena().getManager().leave(pl);
            
            //Выполняем выход по умолчанию (например телепортация на спавн, выдача предмета)
            this.defaultAction.performDefaultLeaveAction(pl);
        }
    }
    
    public IGamePlayer getGamePlayer(String name) {
        return this.players.get(name);
    }
    
    public Collection<IGamePlayer> getAllPlayers() {
        return this.players.values();
    }
    
    public RegenGame getRegenGameByName(String name) {
        return this.regenData.get(name);
    }
    
    public void setRegenGame(ClipboardRegenableGameArena arena, boolean needRegen) throws ConsoleGameException {
        if (arena == null) {
            throw new ConsoleGameException("Арена не может быть нулем!");
        }
        
        String gameName = arena.getManager().getGameName();
        RegenGame rg = this.regenData.get(gameName);
        if (rg == null) {
            RegenGame newRg = new RegenGame(gameName, new ConcurrentHashMap<>());
            this.regenData.put(gameName, newRg);
            rg = newRg;
        }
        
        RegenArena ra = rg.getRegenArena(arena.getName());
        if (ra == null) {
            RegenArena newRa = new RegenArena(arena.getName(), false);
            rg.addRegenArena(newRa);
            ra = newRa;
        }
        
        ra.setNeedRegen(needRegen);
        
        //Сохраняем это дело в другом потоке отведенным под это
        queueSaveTask(() -> {
            saveRegenData();
        });
    }
   
    @SuppressWarnings("unchecked")
    public void loadRegenData() {
        ConcurrentMap<String, RegenGame> regenData = new ConcurrentHashMap<>();
        
        GameControl gc = GameControl.getInstance();
        gc.reloadConfig();
        
        List<RegenGame> rg = (List<RegenGame>) gc.getConfig().getList("regenData");
        if (rg != null) {
            for (RegenGame rr : rg) {
                regenData.put(rr.getName(), rr);
            }
        }
        
        this.regenData = regenData;
    }
    
    public void saveRegenData() {
        GameControl gc = GameControl.getInstance();
        
        FileConfiguration fc = gc.getConfig();
        fc.set("regenData", null);
        
        List<RegenGame> rr = new ArrayList<>(this.regenData.values());
        fc.set("regenData", rr);
        
        gc.saveConfig();
    }
}
