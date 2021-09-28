package ru.boomearo.gamecontrol.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.TradeSelectEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onCraftItemEvent(CraftItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity en = e.getWhoClicked();
        if (en instanceof Player pl) {

            if (pl.hasPermission("gamecontrol.bypass")) {
                return;
            }
        }
        e.setCancelled(true);
        e.setResult(Result.DENY);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity en = e.getWhoClicked();
        if (en instanceof Player pl) {

            if (pl.hasPermission("gamecontrol.bypass")) {
                return;
            }
        }
        e.setCancelled(true);
        e.setResult(Result.DENY);
    }

    @EventHandler
    public void onInventoryPickupItemEvent(InventoryPickupItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onTradeSelectEvent(TradeSelectEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
}
