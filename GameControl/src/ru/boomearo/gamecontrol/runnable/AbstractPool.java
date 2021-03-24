package ru.boomearo.gamecontrol.runnable;

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
    
    //TODO когда пофиксят FAWE тогда будем ожидать в основном потоке
    public void stop() throws InterruptedException {
        //long start = System.currentTimeMillis();
        //GameControl.getInstance().getLogger().info("Ожидаем закрытия пула регенерации арен..");
        //this.pool.shutdown();
        //this.pool.awaitTermination(2, TimeUnit.MINUTES);
        //long end = System.currentTimeMillis();
        //GameControl.getInstance().getLogger().info("Пул регенераций закрыт за " + (end - start) + "мс");
    }
    
}
