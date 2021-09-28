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
     * @see AbstractGameArena
     */
    public AbstractGameArena getArena();

    /**
     * Описывает действие, которое должно произойти когда арена сменила свое состояние на текущее.
     */
    public void initState();

    /**
     * Описывает действие, которое должно циклически происходить.
     * Например, для обработки таймаута игры или прочих игровых особенностей.
     * А так же, именно здесь должна происходить обработка и переключение всех состояний игры.
     */
    public void autoUpdateHandler();
}
