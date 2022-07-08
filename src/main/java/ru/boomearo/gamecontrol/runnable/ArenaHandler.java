package ru.boomearo.gamecontrol.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.managers.GameManager;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.states.game.IGameState;

/**
 * Обработчик всех игр и арен. Каждый тик обрабатывает все игры.
 */
public class ArenaHandler extends BukkitRunnable {

    private final GameManager gameManager;

    public ArenaHandler(GameManager gameManager) {
        this.gameManager = gameManager;
        runnable();
    }

    private void runnable() {
        this.runTaskTimer(GameControl.getInstance(), 1, 1);
    }

    @Override
    public void run() {
        try {
            for (IGameManager<? extends IGamePlayer> game : this.gameManager.getAllGameManagers()) {
                for (AbstractGameArena<? extends IGamePlayer> arena : game.getAllArenas()) {
                    try {
                        IGameState state = arena.getState();
                        //TODO На всякий случай проверяем возвращаемый статус игры на нулл
                        if (state != null) {
                            state.autoUpdateHandler();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
