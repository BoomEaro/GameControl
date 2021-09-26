package ru.boomearo.gamecontrol.exceptions;

/**
 * Исключение, которое бросается в любой не понятной ситуации и должно быть отправлено только в консоль
 */
public class ConsoleGameException extends GameControlException {

    private static final long serialVersionUID = 8458229479955817485L;

    public ConsoleGameException(String msg) {
        super(msg);
    }

}
