package ru.boomearo.gamecontrol;

import java.util.HashSet;

import org.bukkit.plugin.java.JavaPlugin;

import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.listeners.PlayerListener;
import ru.boomearo.gamecontrol.managers.GameManager;
import ru.boomearo.gamecontrol.utils.Vault;

public class GameControl extends JavaPlugin {

    private GameManager manager = null;
    
    private static GameControl instance = null;

    @Override
    public void onEnable() {
        instance = this;
        
        Vault.setupEconomy();
        
        if (this.manager == null) {
            this.manager = new GameManager();
        }
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
        getLogger().info("Плагин успешно запущен.");
    }
    
    @Override
    public void onDisable() {
        this.manager.getRegenPool().stop();
        
        for (Class<? extends JavaPlugin> cl : new HashSet<Class<? extends JavaPlugin>>(this.manager.getAllGameClasses())) {
            try {
                this.manager.unregisterGame(cl);
            } 
            catch (ConsoleGameException e) {
                e.printStackTrace();
            }
        }
        getLogger().info("Плагин успешно выключен.");
    }
    
    public GameManager getGameManager() {
        return this.manager;
    }
    
    public static GameControl getInstance() {
        return instance;
    }
    
}
