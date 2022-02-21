package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultStatsData implements IStatsData {

    private final IStatsType type;

    private final ConcurrentMap<String, DefaultStatsPlayer> players = new ConcurrentHashMap<>();

    public DefaultStatsData(IStatsType type) {
        this.type = type;
    }

    @Override
    public IStatsType getStatsType() {
        return this.type;
    }

    @Override
    public DefaultStatsPlayer getStatsPlayer(String name) {
        return this.players.get(name);
    }

    @Override
    public Collection<DefaultStatsPlayer> getAllStatsPlayer() {
        return this.players.values();
    }

    public void addStatsPlayer(DefaultStatsPlayer data) {
        this.players.put(data.getName(), data);
    }

}
