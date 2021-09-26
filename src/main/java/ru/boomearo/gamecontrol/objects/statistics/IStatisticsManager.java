package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;

/**
 * Базовое представление о статистике в любой игре
 */
public interface IStatisticsManager {

    /**
     * @return тип статистики по указанному имени
     */
    public IStatsData getStatsData(String name);

    /**
     * @return коллекцию, содержащую все типы статистик
     */
    public Collection<? extends IStatsData> getAllStatsData();
}
