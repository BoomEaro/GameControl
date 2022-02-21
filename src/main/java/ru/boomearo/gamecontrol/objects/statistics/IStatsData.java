package ru.boomearo.gamecontrol.objects.statistics;

import java.util.Collection;

/**
 * Базовое представление всех игроков этой статистики
 */
public interface IStatsData {

    /**
     * @return Название статистики
     */
    public IStatsType getStatsType();

    /**
     * @return Игрока статистики по указанному имени
     * @see IStatsPlayer
     */
    public IStatsPlayer getStatsPlayer(String name);

    /**
     * @return Коллекцию, содержащую всех игроков этой статистики
     */
    public Collection<? extends IStatsPlayer> getAllStatsPlayer();

}
