package ru.boomearo.gamecontrol.runnable;

import java.util.concurrent.TimeUnit;

//TODO TEST
public class DatabaseStatsSaver extends AbstractTimer {

    public DatabaseStatsSaver() {
        super("DatabaseStatsSaver", TimeUnit.MINUTES, 5);
    }

    @Override
    public void task() throws Throwable {

    }

}
