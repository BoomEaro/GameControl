package ru.boomearo.gamecontrol;

import org.bukkit.plugin.java.JavaPlugin;

public class GameControl extends JavaPlugin {

    private static GameControl instance = null;

    public void onEnable() {
        instance = this;
        getLogger().info("Плагин успешно запущен.");
    }
    
    public void onDisable() {
        getLogger().info("Плагин успешно выключен.");
    }
    
    public static GameControl getInstance() {
        return instance;
    }
    
}
