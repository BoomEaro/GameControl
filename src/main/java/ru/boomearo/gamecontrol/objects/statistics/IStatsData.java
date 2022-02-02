package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;

/**
 * Базовое представление всех игроков этой типа статистики
 */
public interface IStatsData {

    /**
     * @return Название статистики
     */
    public String getName();

    /**
     * @return Игрока статистики по указанному имени
     * @see StatsPlayer
     */
    public StatsPlayer getStatsPlayer(String name);

    /**
     * @return Коллекцию, содержащую всех игроков этой статистики
     */
    public Collection<? extends StatsPlayer> getAllStatsPlayer();

}
