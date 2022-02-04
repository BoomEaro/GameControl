package ru.boomearo.gamecontrol.objects.states.game;

/**
 * Базовое представление состояния регенерации арены
 */
public interface IRegenState extends IGameState {

    /**
     * @return Ожидать ли регенерации арены
     */
    public boolean isWaitingRegen();

    /**
     * Установить ожидание регенерации
     * @param waitingRegen Ожидать ли регенерацию
     */
    public void setWaitingRegen(boolean waitingRegen);

}
