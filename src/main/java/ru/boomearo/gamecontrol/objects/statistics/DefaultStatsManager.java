package ru.boomearo.gamecontrol.objects.statistics;

import ru.boomearo.gamecontrol.objects.statistics.database.DefaultStatsDatabase;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultStatsManager implements IStatisticsManager {

    private final DefaultStatsDatabase database;

    private final ConcurrentMap<IStatsType, DefaultStatsData> stats = new ConcurrentHashMap<>();

    public DefaultStatsManager(DefaultStatsDatabase database) {
        this.database = database;
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
    public void loadAllStatsData() {
        try {
            this.database.initDatabase();

            for (IStatsType type : this.database.getTables()) {
                DefaultStatsData data = getStatsData(type);
                for (DefaultStatsDatabase.SectionStats stats : this.database.getAllStatsData(type).get()) {
                    data.addStatsPlayer(new DefaultStatsPlayer(stats.name, stats.value));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAllStatsData() {
        try {
            for (DefaultStatsData dsd : this.stats.values()) {
                for (DefaultStatsPlayer dsp : dsd.getAllStatsPlayer()) {
                    if (dsp.hasChanges()) {
                        this.database.insertOrUpdateStatsData(dsd.getStatsType(), dsp.getName(), dsp.getValue());

                        dsp.setChanges(false);
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

    public double getStatsFromPlayer(IStatsType type, String name) {
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
