package ru.boomearo.gamecontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

import ru.boomearo.gamecontrol.GameControl;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player pl = e.getPlayer();
        
        try {
            GameControl.getInstance().getGameManager().leaveGame(pl);
        } 
        catch (Exception e1) {}
    }
    
    @EventHandler
    public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    
    @EventHandler
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerBucketEvent(PlayerBucketEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerBucketFillEvent(PlayerBucketFillEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    
    @EventHandler
    public void onPlayerEditBookEvent(PlayerEditBookEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerHarvestBlockEvent(PlayerHarvestBlockEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerPortalEvent(PlayerPortalEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerShearEntityEvent(PlayerShearEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerTakeLecternBookEvent(PlayerTakeLecternBookEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerUnleashEntityEvent(PlayerUnleashEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
}
