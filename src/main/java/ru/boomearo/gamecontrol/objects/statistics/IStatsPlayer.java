package ru.boomearo.gamecontrol.objects.statistics;

public interface IStatsPlayer extends Comparable<IStatsPlayer> {

    String getName();

    String getFormattedValues();

    boolean hasChanges();

    void setChanges(boolean changes);

}
