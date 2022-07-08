package ru.boomearo.gamecontrol;

import java.io.File;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.spawn.EssentialsSpawn;

import ru.boomearo.gamecontrol.commands.gamecontrol.CmdExecutorGameControl;
import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.listeners.BlockListener;
import ru.boomearo.gamecontrol.listeners.EntityListener;
import ru.boomearo.gamecontrol.listeners.HangingListener;
import ru.boomearo.gamecontrol.listeners.InventoryListener;
import ru.boomearo.gamecontrol.listeners.PlayerListener;
import ru.boomearo.gamecontrol.listeners.RaidListener;
import ru.boomearo.gamecontrol.listeners.VehicleListener;
import ru.boomearo.gamecontrol.listeners.WeatherListener;
import ru.boomearo.gamecontrol.managers.GameManager;
import ru.boomearo.gamecontrol.objects.StoredRegenArena;
import ru.boomearo.gamecontrol.objects.StoredRegenGame;
import ru.boomearo.gamecontrol.objects.region.CuboidRegion;
import ru.boomearo.gamecontrol.runnable.ArenaHandler;

public class GameControl extends JavaPlugin {

    private GameManager gameManager = null;
    private ArenaHandler arenaHandler = null;

    private static GameControl instance = null;

    @Override
    public void onEnable() {
        instance = this;

        EssentialsSpawn essentialsSpawn = (EssentialsSpawn) Bukkit.getPluginManager().getPlugin("EssentialsSpawn");

        ConfigurationSerialization.registerClass(CuboidRegion.class);
        ConfigurationSerialization.registerClass(StoredRegenGame.class);
        ConfigurationSerialization.registerClass(StoredRegenArena.class);

        File configFile = new File(getDataFolder() + File.separator + "config.yml");
        if (!configFile.exists()) {
            getLogger().info("Конфиг не найден, создаю новый...");
            saveDefaultConfig();
        }

        if (this.gameManager == null) {
            this.gameManager = new GameManager(essentialsSpawn.getSpawn("default"));

            this.gameManager.loadRegenData();

            this.gameManager.initRegenPool();
            this.gameManager.initSavePool();
        }

        if (this.arenaHandler == null) {
            this.arenaHandler = new ArenaHandler(this.gameManager);
        }

        getCommand("gamecontrol").setExecutor(new CmdExecutorGameControl(this.gameManager));

        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new HangingListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this.gameManager), this);
        getServer().getPluginManager().registerEvents(new RaidListener(), this);
        getServer().getPluginManager().registerEvents(new VehicleListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);

        getLogger().info("Плагин успешно запущен.");
    }

    @Override
    public void onDisable() {
        this.gameManager.saveRegenData();

        try {
            this.gameManager.stopSavePool();
        }
        catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        for (Class<? extends JavaPlugin> cl : new HashSet<>(this.gameManager.getAllGameClasses())) {
            try {
                this.gameManager.unregisterGame(cl);
            }
            catch (ConsoleGameException e) {
                e.printStackTrace();
            }
        }

        ConfigurationSerialization.unregisterClass(CuboidRegion.class);
        ConfigurationSerialization.unregisterClass(StoredRegenGame.class);
        ConfigurationSerialization.unregisterClass(StoredRegenArena.class);

        getLogger().info("Плагин успешно выключен.");
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public static GameControl getInstance() {
        return instance;
    }

    /**
     * Центрирует локацию относительно блока (добавляет оси 'X' и 'Z' значение 0.5)
     * @param loc Локация которую нужно центрировать
     * @return Нормализованную локацию
     */
    public static Location normalizeLocation(Location loc) {
        return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f);
    }

    /**
     * Центрирует направления взгляда локации строго по одной из четырех сторон света, а так же центрирует локацию относительно блока.
     * @param loc Локация которую нужно центрировать
     * @return Нормализованную локацию
     */
    public static Location normalizeRotation(Location loc) {
        float yaw = loc.getYaw();
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw >= 315 || yaw < 45) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 0f, 0f);
        }
        else if (yaw < 135) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 90f, 0f);
        }
        else if (yaw < 225) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 180f, 0f);
        }
        else if (yaw < 315) {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, -90f, 0f);
        }
        else {
            return new Location(loc.getWorld(), loc.getBlockX() + 0.5f, loc.getBlockY(), loc.getBlockZ() + 0.5f, 180f, 0f);
        }
    }

}
