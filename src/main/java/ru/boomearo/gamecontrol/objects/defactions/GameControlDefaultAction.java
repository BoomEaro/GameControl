package ru.boomearo.gamecontrol.objects.defactions;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import ru.boomearo.gamecontrol.GameControl;
import ru.boomearo.gamecontrol.utils.ExpFix;

/**
 * Реализация IDefaultAction, которая используется по умолчанию, если никакой плагин не зарегистрировал свою реализацию.
 */
public class GameControlDefaultAction implements IDefaultAction {

    @Override
    public Location getDefaultSpawnLocation() {
        Location loc = GameControl.getInstance().getEssentialsSpawn().getSpawn("default");
        if (loc == null) {
            return new Location(Bukkit.getWorld("world"), 0, 90, 0);
        }
        return loc;
    }

    @Override
    public void performDefaultJoinAction(Player pl) {
        if (Bukkit.isPrimaryThread()) {
            taskJoin(pl);
            return;
        }

        Bukkit.getScheduler().runTask(GameControl.getInstance(), () -> {
            taskJoin(pl);
        });
    }

    @Override
    public void performDefaultLeaveAction(Player pl) {
        if (Bukkit.isPrimaryThread()) {
            taskLeave(pl);
            return;
        }

        Bukkit.getScheduler().runTask(GameControl.getInstance(), () -> {
            taskLeave(pl);
        });
    }

    private void taskJoin(Player pl) {
        for (PotionEffect pr : pl.getActivePotionEffects()) {
            pl.removePotionEffect(pr.getType());
        }

        pl.setFoodLevel(20);
        pl.setExhaustion(2);

        pl.setHealth(pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        pl.setFireTicks(0);

        pl.setArrowsInBody(0);

        pl.setGameMode(GameMode.ADVENTURE);

        ExpFix.setTotalExperience(pl, 0);

        pl.getInventory().clear();

        pl.closeInventory();
    }

    private void taskLeave(Player pl) {
        pl.teleport(getDefaultSpawnLocation());

        for (PotionEffect pr : pl.getActivePotionEffects()) {
            pl.removePotionEffect(pr.getType());
        }

        pl.setFoodLevel(20);
        pl.setExhaustion(2);

        pl.setHealth(pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        pl.setFireTicks(0);

        pl.setArrowsInBody(0);

        pl.setGameMode(GameMode.ADVENTURE);

        ExpFix.setTotalExperience(pl, 0);

        pl.getInventory().clear();

        pl.closeInventory();
    }

}
