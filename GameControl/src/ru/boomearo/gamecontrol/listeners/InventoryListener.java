package ru.boomearo.gamecontrol.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
    
    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
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
