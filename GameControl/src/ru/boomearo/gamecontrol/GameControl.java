package ru.boomearo.gamecontrol;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.spawn.EssentialsSpawn;

import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.listeners.PlayerListener;
import ru.boomearo.gamecontrol.managers.GameManager;
import ru.boomearo.gamecontrol.utils.NumberUtils;
import ru.boomearo.gamecontrol.utils.Vault;

public class GameControl extends JavaPlugin {

    private GameManager manager = null;
    
    private EssentialsSpawn essSpawn = null;
    
    private static GameControl instance = null;
    
    @Override
    public void onEnable() {
        instance = this;
        
        this.essSpawn = (EssentialsSpawn) Bukkit.getPluginManager().getPlugin("EssentialsSpawn");
        
        Vault.setupEconomy();
        
        if (this.manager == null) {
            this.manager = new GameManager();
        }
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
        getLogger().info("Плагин успешно запущен.");
    }
    
    @Override
    public void onDisable() {
        try {
            this.manager.getRegenPool().stop();
        }
        catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
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
    
    public EssentialsSpawn getEssentialsSpawn() {
        return this.essSpawn;
    }
    
    public static GameControl getInstance() {
        return instance;
    }
    
    public static Location getSpawnLocation() {
        return GameControl.getInstance().getEssentialsSpawn().getSpawn("default");
    }
    
    public static String getFormatedEco(double price) {
        return miniForm(price, "§a") + "§a§lⵥ";
    }

    private static String miniForm(double money, String color) {
        boolean isNegative = false;
        if (money < 0) {
            isNegative = true;
        }
        double newMoney = money;

        if (isNegative) {
            newMoney = -money;
        }
        if (newMoney < 1000000.0D) {
            return (isNegative ? "§c-" : "") + color + NumberUtils.displayCurrency(newMoney);
        }
        if (newMoney < 1.0E9D) {
            return (isNegative ? "§c-" : "") + color + NumberUtils.displayCurrency(newMoney / 1000000.0D) + "M";
        }
        if (newMoney < 1.0E12D) {
            return (isNegative ? "§c-" : "") + color + NumberUtils.displayCurrency(newMoney / 1.0E9D) + "B";
        }
        if (newMoney < 1.0E15D) {
            return (isNegative ? "§c-" : "") + color + NumberUtils.displayCurrency(newMoney / 1.0E12D) + "T";
        }
        if (newMoney < 1.0E18D) {
            return (isNegative ? "§c-" : "") + color + NumberUtils.displayCurrency(newMoney / 1.0E15D) + "Q";
        }
        return (isNegative ? "§c-" : "") + color + NumberUtils.displayCurrency(newMoney);
    }
    
    public static Location normalizeLocation(Location loc) {
        return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f);
    }
    
    public static Location normalizeRotation(Location loc) {
        float yaw = loc.getYaw();
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw >= 315 || yaw < 45) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 0f, 0f);
            //BedWars.getInstance().getLogger().info("test south " + yaw);
        } 
        else if (yaw < 135) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 90f, 0f);
            //BedWars.getInstance().getLogger().info("test west " + yaw);
        } 
        else if (yaw < 225) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 180f, 0f);
            //BedWars.getInstance().getLogger().info("test north " + yaw);
        } 
        else if (yaw < 315) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, -90f, 0f);
            //BedWars.getInstance().getLogger().info("test east " + yaw);
        }
        else {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 180f, 0f);
            //BedWars.getInstance().getLogger().info("test north " + yaw);  
        }
    }
    
}
