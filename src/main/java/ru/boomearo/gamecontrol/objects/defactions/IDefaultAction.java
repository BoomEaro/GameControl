package ru.boomearo.gamecontrol.objects.defactions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Базовое представление задач по умолчанию, которые должны быть зарегистрированы любым плагином для своих нужд
 */
public interface IDefaultAction {

    /**
     * @return локацию спавна, куда будут попадать игроки после выхода из игры
     * @see Location
     */
    public Location getDefaultSpawnLocation();


    /**
     * Описывает действие, которое выполняется перед входом в любую игру.
     * Обычно используется для очистки инвентаря, сброса скорости и тд.
     */
    public void performDefaultJoinAction(Player pl);

    /**
     * Описывает действие, которое выполняется перед тем, как игрок должен покинуть игру.
     * Обычно используется для очистки инвентаря, сброса скорости и тд.
     */
    public void performDefaultLeaveAction(Player pl);
}
