package ru.boomearo.gamecontrol.objects;

/**
 * Используется для представления игры как принудительно запускаемой, где это возможно
 */
public interface IForceStartable {

    public boolean isForceStarted();

    public void setForceStarted(boolean force);

}
