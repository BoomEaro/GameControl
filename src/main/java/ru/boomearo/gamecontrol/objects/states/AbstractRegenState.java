package ru.boomearo.gamecontrol.objects.states;

/**
 * Абстракция, реализующая состояние ожидания регенерации.
 */
public abstract class AbstractRegenState implements IRegenState {

    private volatile boolean waitingRegen = false;

    @Override
    public boolean isWaitingRegen() {
        return this.waitingRegen;
    }

    @Override
    public void setWaitingRegen(boolean waitingRegen) {
        this.waitingRegen = waitingRegen;
    }
}
