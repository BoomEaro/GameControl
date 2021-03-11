package ru.boomearo.gamecontrol.runnable;

public class RegenPool extends AbstractPool {

    public RegenPool() {
        super(new AdvThreadFactory("RegenPool", 3));
    }

}
