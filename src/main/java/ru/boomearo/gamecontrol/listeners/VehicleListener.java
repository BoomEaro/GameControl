package ru.boomearo.gamecontrol.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class VehicleListener implements Listener {

    @EventHandler
    public void onVehicleCreateEvent(VehicleCreateEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onVehicleDamageEvent(VehicleDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity en = e.getAttacker();
        if (en instanceof Player pl) {

            if (pl.hasPermission("gamecontrol.bypass")) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onVehicleDestroyEvent(VehicleDestroyEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity en = e.getAttacker();
        if (en instanceof Player pl) {

            if (pl.hasPermission("gamecontrol.bypass")) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onVehicleEnterEvent(VehicleEnterEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity en = e.getEntered();
        if (en instanceof Player pl) {

            if (pl.hasPermission("gamecontrol.bypass")) {
                return;
            }
        }
        e.setCancelled(true);
    }

}
