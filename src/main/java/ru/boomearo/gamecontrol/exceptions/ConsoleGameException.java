package ru.boomearo.gamecontrol.exceptions;

/**
 * Исключение, которое бросается в любой не понятной ситуации и должно быть отправлено только в консоль
 */
public class ConsoleGameException extends GameControlException {

    public ConsoleGameException(String msg) {
        super(msg);
    }

}
