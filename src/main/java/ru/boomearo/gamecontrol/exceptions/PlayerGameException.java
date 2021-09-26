package ru.boomearo.gamecontrol.exceptions;

/**
 * Исключение, которое бросается для игроков и может быть нормально отображено в чате или где-либо еще
 */
public class PlayerGameException extends GameControlException {

    private static final long serialVersionUID = -773699118368352657L;

    public PlayerGameException(String msg) {
        super(msg);
    }

}
