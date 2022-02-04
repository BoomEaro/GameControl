package ru.boomearo.gamecontrol.objects.states.game;

/**
 * Базовое представление состояния арены, в которой имеется временной лимит игры
 */
public interface ICountable {

    public int getCount();

    public void setCount(int count);

}
