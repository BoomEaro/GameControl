package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;

/**
 * Базовое представление о статистике в любой игре
 */
public interface IStatisticsManager {

    /**
     * @return Всех игроков этой статистики
     * @see IStatsData
     */
    public IStatsData getStatsData(IStatsType type);

    /**
     * @return Коллекцию, содержащую все типы статистик
     */
    public Collection<? extends IStatsData> getAllStatsData();
}
