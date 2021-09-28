package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;

/**
 * Базовое представление об статистике игрока
 */
public interface IStatsData {

    /**
     * @return ниу игрока
     */
    public String getName();

    /**
     * @return игрока статистики по указанному имени
     * @see StatsPlayer
     */
    public StatsPlayer getStatsPlayer(String name);

    /**
     * @return коллекцию, содержащую всех игроков этой статистики
     */
    public Collection<? extends StatsPlayer> getAllStatsPlayer();

}
