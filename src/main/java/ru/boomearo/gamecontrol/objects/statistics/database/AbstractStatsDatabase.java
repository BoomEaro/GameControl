package ru.boomearo.gamecontrol.objects.statistics.database;

import org.bukkit.plugin.java.JavaPlugin;

import org.sqlite.JDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractStatsDatabase {

    private final JavaPlugin plugin;

    protected Connection connection = null;

    private static final String CON_STR = "jdbc:sqlite:[path]database.db";

    public AbstractStatsDatabase(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public void initDatabase() throws SQLException {
        DriverManager.registerDriver(new JDBC());

        this.connection = DriverManager.getConnection(CON_STR.replace("[path]", this.plugin.getDataFolder() + File.separator));

        onCreatedConnection(this.connection);
    }

    public void disconnect() throws SQLException {
        this.connection.close();
    }

    protected abstract void onCreatedConnection(Connection connection);
}
