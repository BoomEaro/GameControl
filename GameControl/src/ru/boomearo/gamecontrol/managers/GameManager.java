package ru.boomearo.gamecontrol.managers;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.exceptions.GameControlException;
import ru.boomearo.gamecontrol.exceptions.PlayerGameException;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.runnable.AdvThreadFactory;
import ru.boomearo.gamecontrol.runnable.RegenTask;

public final class GameManager {

    private final ConcurrentMap<Class<? extends JavaPlugin>, IGameManager> gamesClasses = new ConcurrentHashMap<Class<? extends JavaPlugin>, IGameManager>();
    private final ConcurrentMap<String, IGameManager> gamesNames = new ConcurrentHashMap<String, IGameManager>();
    
    private final ConcurrentMap<String, IGamePlayer> players = new ConcurrentHashMap<String, IGamePlayer>();
    
    private ThreadPoolExecutor regenPool = null;
    
    private final Object lock = new Object();
    
    public static final String prefix = "§8[§9GameControl§8]: §7";
   
    public void initRegenPool() {
        if (this.regenPool == null) {
            this.regenPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, new AdvThreadFactory("ArenaRegen", 3));
        }
    }
    
    public void stopRegenPool() {
        if (this.regenPool != null) {
            this.regenPool.shutdown();
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
                catch (GameControlException e) {}
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
            
            
            //Вернет игрока если удалось войти в игру. Если войти не удалось, должено быть любое исключение этого плагина.
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
            
            //Сначала удлаляем в общей, даже если произойдет исключение, выход из игры должен быть ВСЕГДА
            this.players.remove(pl.getName());
            
            igp.getArena().getManager().leave(pl);
        }
    }
    
    public IGamePlayer getGamePlayer(String name) {
        return this.players.get(name);
    }
    
    public Collection<IGamePlayer> getAllPlayers() {
        return this.players.values();
    }
}
