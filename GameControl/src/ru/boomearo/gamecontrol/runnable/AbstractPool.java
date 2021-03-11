package ru.boomearo.gamecontrol.runnable;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractPool {

    private final ThreadPoolExecutor pool;
    
    public AbstractPool(ThreadFactory factory) {
        this.pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, factory);
    }
    
    public void addTask(Runnable task) {
        this.pool.execute(task);
    }
    
    public void stop() {
        List<Runnable> ru = this.pool.shutdownNow();
        //Выполняем оставшиеся задачи в момент выключения
        for (Runnable r : ru) {
            r.run();
        }
    }
    
}
