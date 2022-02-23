package ru.boomearo.gamecontrol.objects.statistics;

import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;

import java.util.Collection;

/**
 * Базовое представление о статистике в любой игре
 */
public interface IStatisticsManager {

    /**
     * @return Менеджер игры, который использует эту статистику
     */
    public IGameManager<? extends IGamePlayer> getGameManager();

    /**
     * @return Всех игроков этой статистики
     * @see IStatsData
     */
    public IStatsData getStatsData(IStatsType type);

    /**
     * @return Коллекцию, содержащую все типы статистик
     */
    public Collection<? extends IStatsData> getAllStatsData();

    /**
     * @return Все типы статистики
     */
    public IStatsType[] getAllStatsType();

    /**
     * Логика запуска менеджера статистики
     */
    public void onEnable();

    /**
     * Логика выключения менеджера статистики
     */
    public void onDisable();

    /**
     * Логика сохранения всех данных статистики на диск. Обычно выполняется шедулером каждые 5 минут.
     * Сохраняет все данные в ТЕКУЩЕМ потоке, поэтому рекомендуется выполнять этот метод в любом месте, но не в основном потоке.
     */
    public void onSaveAllData();
}
