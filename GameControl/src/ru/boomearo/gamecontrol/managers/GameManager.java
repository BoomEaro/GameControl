package ru.boomearo.gamecontrol.managers;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.exceptions.PlayerGameException;
import ru.boomearo.gamecontrol.objects.IGameArena;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.runnable.RegenPool;

public final class GameManager {

    private final ConcurrentMap<Class<? extends JavaPlugin>, IGameManager> games = new ConcurrentHashMap<Class<? extends JavaPlugin>, IGameManager>();
    private final ConcurrentMap<String, IGamePlayer> players = new ConcurrentHashMap<String, IGamePlayer>();
    
    private final RegenPool pool = new RegenPool();
    
    private final Object lock = new Object();
    
    public RegenPool getRegenPool() {
        return this.pool;
    }
    
    public void queueRegenArena(IGameArena arena) {
        if (arena == null) {
            return;
        }
        
        this.pool.addTask(() -> {
            arena.regen();
        });
    }
    
    public void registerGame(Class<? extends JavaPlugin> clazz, IGameManager manager) throws ConsoleGameException {
        if (clazz == null || manager == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }
        
        synchronized (this.lock) {
            IGameManager igm = this.games.get(clazz);
            if (igm != null) {
                throw new ConsoleGameException("Игра " + igm.getGameName() + " уже зарегистрирована!");
            }
            
            this.games.put(clazz, manager);
            GameControl.getInstance().getLogger().info("Игра " + manager.getGameName() + " успешно зарегистрирована!");
        }
    }
    
    public void unregisterGame(Class<? extends JavaPlugin> clazz) throws ConsoleGameException {
        if (clazz == null) {
            throw new ConsoleGameException("Аргументы не должны быть нулевые!");
        }
        
        synchronized (this.lock) {
            IGameManager igm = this.games.get(clazz);
            if (igm == null) {
                throw new ConsoleGameException("Класс " + clazz.getName() + " не был зарегистрирован!");
            }
            
            this.games.remove(clazz);
            GameControl.getInstance().getLogger().info("Класс " + clazz.getName() + " больше не зарегистрирован.");
        }
    }
    
    public IGameManager getGameByClass(Class<? extends JavaPlugin> clazz) {
        return this.games.get(clazz);
    }
    public Collection<IGameManager> getAllGameManagers() {
        return this.games.values();
    }
    public Set<Class<? extends JavaPlugin>> getAllGameClasses() {
        return this.games.keySet();
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
            
            IGameManager igm = this.games.get(clazz);
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

            igp.getArena().getManager().leave(pl);
            
            this.players.remove(pl.getName());
        }
    }
    
    public IGamePlayer getGamePlayer(String name) {
        return this.players.get(name);
    }
    
    public Collection<IGamePlayer> getAllPlayers() {
        return this.players.values();
    }
}
