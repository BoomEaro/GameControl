package ru.boomearo.gamecontrol.objects.statistics;

public interface IStatsPlayer extends Comparable<IStatsPlayer> {

    String getName();

    boolean hasChanges();

    void setChanges(boolean changes);

}
