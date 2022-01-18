package ru.boomearo.gamecontrol.exceptions;

/**
 * Исключение, которое бросается для игроков и может быть нормально отображено в чате или где-либо еще
 */
public class PlayerGameException extends GameControlException {

    public PlayerGameException(String msg) {
        super(msg);
    }

}
