package ru.boomearo.gamecontrol.objects.statistics;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.statistics.database.DefaultStatsDatabase;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultStatsManager implements IStatisticsManager {

    private final IGameManager<? extends IGamePlayer> gameManager;
    private final IStatsType[] types;
    private final DefaultStatsDatabase database;
    private final ConcurrentMap<IStatsType, DefaultStatsData> stats = new ConcurrentHashMap<>();

    public DefaultStatsManager(IGameManager<? extends IGamePlayer> gameManager, IStatsType[] types) {
        this.gameManager = gameManager;
        this.types = types;

        for (IStatsType type : this.types) {
            this.stats.put(type, new DefaultStatsData(type));
        }

        this.database = new DefaultStatsDatabase(gameManager.getPlugin(), types);
    }

    @Override
    public IGameManager<? extends IGamePlayer> getGameManager() {
        return this.gameManager;
    }

    @Override
    public DefaultStatsData getStatsData(IStatsType type) {
        return this.stats.get(type);
    }

    @Override
    public Collection<DefaultStatsData> getAllStatsData() {
        return this.stats.values();
    }

    @Override
    public IStatsType[] getAllStatsType() {
        return this.types;
    }

    @Override
    public void onEnable() {
        try {
            this.database.initDatabase();

            for (IStatsType type : this.types) {
                DefaultStatsData data = getStatsData(type);
                for (DefaultStatsDatabase.SectionStats stats : this.database.getAllStatsData(type)) {
                    data.addStatsPlayer(new DefaultStatsPlayer(stats.name, stats.value));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            this.database.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveAllData() {
        try {
            for (DefaultStatsData statsData : this.stats.values()) {
                //TODO TEST1
                GameControl.getInstance().getLogger().info("TEST1 " + statsData.getStatsType().getName());
                for (DefaultStatsPlayer playerData : statsData.getAllStatsPlayer()) {
                    //TODO TEST2
                    GameControl.getInstance().getLogger().info("TEST2 " + playerData.getName() + " " + playerData.hasChanges());
                    if (playerData.hasChanges()) {
                        this.database.insertOrUpdateStatsData(statsData.getStatsType(), playerData.getName(), playerData.getValue());
                        playerData.setChanges(false);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStatsToPlayer(IStatsType type, String name) {
        DefaultStatsData data = this.stats.get(type);
        DefaultStatsPlayer sp = data.getStatsPlayer(name);
        if (sp == null) {
            DefaultStatsPlayer newSp = new DefaultStatsPlayer(name, 0);
            data.addStatsPlayer(newSp);
            sp = newSp;
        }
        sp.setValue(sp.getValue() + 1);
        sp.setChanges(true);
    }

    public double getStatsValueFromPlayer(IStatsType type, String name) {
        DefaultStatsData data = this.stats.get(type);
        if (data == null) {
            return 0;
        }

        DefaultStatsPlayer sp = data.getStatsPlayer(name);
        if (sp == null) {
            return 0;
        }

        return sp.getValue();
    }
}
