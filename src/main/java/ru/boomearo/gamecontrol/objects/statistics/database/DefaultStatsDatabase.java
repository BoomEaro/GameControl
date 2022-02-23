package ru.boomearo.gamecontrol.objects.statistics.database;

import org.bukkit.plugin.java.JavaPlugin;

import ru.boomearo.gamecontrol.objects.statistics.IStatsType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultStatsDatabase extends AbstractStatsDatabase {

    private final IStatsType[] tables;

    public DefaultStatsDatabase(JavaPlugin plugin, IStatsType[] tables) {
        super(plugin);
        this.tables = tables;
    }

    public IStatsType[] getTables() {
        return this.tables;
    }

    @Override
    protected void onCreatedConnection(Connection connection) {
        for (IStatsType tableType : this.tables) {
            createNewTableStatsData(tableType, connection);
        }
    }

    private void createNewTableStatsData(IStatsType tableType, Connection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableType.getTableName() + " (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	name VARCHAR(255) UNIQUE NOT NULL,\n"
                + "	value DOUBLE NOT NULL\n"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            getPlugin().getLogger().info("Таблица " + tableType.getTableName() + " успешно загружена.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SectionStats> getAllStatsData(IStatsType tableType) {
        try (Statement statement = this.connection.createStatement()) {
            List<SectionStats> collections = new ArrayList<>();
            ResultSet resSet = statement.executeQuery("SELECT id, name, value FROM " + tableType.getTableName());
            while (resSet.next()) {
                collections.add(new SectionStats(resSet.getInt("id"), resSet.getString("name"), resSet.getInt("value")));
            }
            return collections;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void insertOrUpdateStatsData(IStatsType tableType, String name, double value) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO " + tableType.getTableName() + "(`name`, `value`) VALUES(?, ?) ON CONFLICT (name) DO UPDATE SET value = EXCLUDED.value")) {
            statement.setString(1, name);
            statement.setDouble(2, value);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class SectionStats {
        public final int id;
        public final String name;
        public final double value;

        public SectionStats(int id, String name, double value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }
    }
}
