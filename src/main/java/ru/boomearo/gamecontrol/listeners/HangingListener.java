package ru.boomearo.gamecontrol.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class HangingListener implements Listener {

    @EventHandler
    public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity remover = e.getRemover();
        if (remover instanceof Player pl) {

            if (pl.hasPermission("gamecontrol.bypass")) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onHangingBreakEvent(HangingBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onHangingPlaceEvent(HangingPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player pl = e.getPlayer();
        if (pl == null) {
            return;
        }
        if (pl.hasPermission("gamecontrol.bypass")) {
            return;
        }
        e.setCancelled(true);
    }

}
