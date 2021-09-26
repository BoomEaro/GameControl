package ru.boomearo.gamecontrol.objects.states;

import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;

/**
 * Базовое представление всех состояний арены
 */
public interface IGameState {

    /**
     * @return название состояния
     */
    public String getName();

    /**
     * @return арена, в которой это состояние имеется
     */
    public AbstractGameArena getArena();

    /**
     * Описывает действие, которое должно произойти когда арена сменила свое состояние на текущее.
     */
    public void initState();

    /**
     * Обработчик событий в арене. Должен вызываться в любом шедулере в основном потоке.
     */
    public void autoUpdateHandler();
}
