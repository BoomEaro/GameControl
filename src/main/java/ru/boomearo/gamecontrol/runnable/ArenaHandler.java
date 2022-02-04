package ru.boomearo.gamecontrol.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.states.IGameState;

/**
 * Обработчик всех игр и арен. Каждый тик обрабатывает все игры.
 */
public class ArenaHandler extends BukkitRunnable {

    public ArenaHandler() {
        runnable();
    }

    private void runnable() {
        this.runTaskTimer(GameControl.getInstance(), 1, 1);
    }

    @Override
    public void run() {
        try {
            for (IGameManager<? extends IGamePlayer> game : GameControl.getInstance().getGameManager().getAllGameManagers()) {
                for (AbstractGameArena<? extends IGamePlayer> arena : game.getAllArenas()) {
                    try {
                        IGameState state = arena.getState();

                        state.autoUpdateHandler();
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
