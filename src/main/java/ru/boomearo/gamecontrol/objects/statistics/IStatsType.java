package ru.boomearo.gamecontrol.objects.statistics;

import org.bukkit.Material;

public interface IStatsType {

    /**
     * Название статистики
     */
    String getName();

    /**
     * Таблица статистики
     */
    String getTableName();

    /**
     * Иконка статистики. Может использовать для других плагинов для визуального удобства
     */
    Material getIcon();

}
