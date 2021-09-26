package ru.boomearo.gamecontrol.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

public class RaidListener implements Listener {

    @EventHandler
    public void onRaidTriggerEvent(RaidTriggerEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }

}
