package ru.boomearo.gamecontrol;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.paperlib.PaperLib;

import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.listeners.PlayerListener;
import ru.boomearo.gamecontrol.managers.GameManager;
import ru.boomearo.gamecontrol.utils.NumberUtils;
import ru.boomearo.gamecontrol.utils.Vault;

public class GameControl extends JavaPlugin {

    private GameManager manager = null;
    
    private Essentials ess = null;
    
    private static GameControl instance = null;

    @Override
    public void onEnable() {
        instance = this;
        
        this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        
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
    
    public Essentials getEssentials() {
        return this.ess;
    }
    
    public void asyncTeleport(Player pl, Location loc) {
        if (pl == null || loc == null) {
            return;
        }
        
        if (Bukkit.isPrimaryThread()) {
            teleport(pl, loc);
            return;
        }
        
        Bukkit.getScheduler().runTask(this, () -> {
            teleport(pl, loc);
        });
    }
    
    private void teleport(Player pl, Location loc) {
        if (!pl.isOnline()) {
            return;
        }
        PaperLib.teleportAsync(pl, loc).thenAccept(result -> {
            if (!result) {
                pl.sendMessage("§cНе удалось телепортироваться! Сообщите Администрации!");
            }
        });
    }
    
    public static GameControl getInstance() {
        return instance;
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
    
}
