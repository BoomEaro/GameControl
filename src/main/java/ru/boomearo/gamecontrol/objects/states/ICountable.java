package ru.boomearo.gamecontrol.objects.states;

/**
 * Базовое представление состояния арены, в которой имеется лимит игры
 */
public interface ICountable {

    public int getCount();

    public void setCount(int count);

}
