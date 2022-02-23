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
