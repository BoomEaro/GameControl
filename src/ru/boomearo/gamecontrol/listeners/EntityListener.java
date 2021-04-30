package ru.boomearo.gamecontrol.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.BatToggleSleepEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PigZombieAngerEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class EntityListener implements Listener {
    
    @EventHandler
    public void onBatToggleSleepEvent(BatToggleSleepEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityBreakDoorEvent(EntityBreakDoorEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    
    @EventHandler
    public void onEntityBreedEvent(EntityBreedEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity en = e.getDamager();
        if (en != null) {
            if (en instanceof Player) {
                Player pl = (Player) en;
                
                if (pl.hasPermission("gamecontrol.bypass")) {
                    return;
                }
            }
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityDropItemEvent(EntityDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityEnterBlockEvent(EntityEnterBlockEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityEnterLoveModeEvent(EntityEnterLoveModeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityInteractEvent(EntityInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityPortalEvent(EntityPortalEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityPortalExitEvent(EntityPortalExitEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntitySpellCastEvent(EntitySpellCastEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityTameEvent(EntityTameEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityTransformEvent(EntityTransformEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPigZombieAngerEvent(PigZombieAngerEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerLeashEntityEvent(PlayerLeashEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onSheepDyeWoolEvent(SheepDyeWoolEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onSheepRegrowWoolEvent(SheepRegrowWoolEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onSlimeSplitEvent(SlimeSplitEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onSpawnerSpawnEvent(SpawnerSpawnEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
}
