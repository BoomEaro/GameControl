package ru.boomearo.gamecontrol.objects.statistics.database;

import org.bukkit.plugin.java.JavaPlugin;

import org.sqlite.JDBC;

import ru.boomearo.serverutils.utils.other.ExtendedThreadFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractStatsDatabase {

    private final JavaPlugin plugin;

    protected Connection connection = null;
    protected ExecutorService executor = null;

    private static final String CON_STR = "jdbc:sqlite:[path]database.db";

    public AbstractStatsDatabase(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public void initDatabase() throws SQLException {
        DriverManager.registerDriver(new JDBC());

        this.executor = Executors.newFixedThreadPool(1, new ExtendedThreadFactory(this.plugin.getName() + "-SQL", 3));

        this.connection = DriverManager.getConnection(CON_STR.replace("[path]", this.plugin.getDataFolder() + File.separator));

        onCreatedConnection(this.connection);
    }

    public void disconnect() throws SQLException, InterruptedException {
        if (this.executor == null || this.connection == null) {
            return;
        }
        this.executor.shutdown();
        this.executor.awaitTermination(15, TimeUnit.SECONDS);
        this.connection.close();
    }

    protected abstract void onCreatedConnection(Connection connection);
}
