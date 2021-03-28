package ru.boomearo.gamecontrol.objects.defactions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.boomearo.gamecontrol.GameControl;

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
    public void performDefaultLeaveAction(Player pl) {
        if (Bukkit.isPrimaryThread()) {
            task(pl);
            return;
        }
        
        Bukkit.getScheduler().runTask(GameControl.getInstance(), () -> {
            task(pl);
        });
    }
    
    private void task(Player pl) {
        pl.teleport(getDefaultSpawnLocation());
    }

}
