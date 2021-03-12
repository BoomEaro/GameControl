package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;

public interface IStatisticsManager {

    public IStatsData getStatsData(String name);
    public Collection<? extends IStatsData> getAllStatsData();
}
