package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;

public interface IStatsData {

    public String getName();
    
    public StatsPlayer getStatsPlayer(String name);
    public Collection<? extends StatsPlayer> getAllStatsPlayer();
    
}
