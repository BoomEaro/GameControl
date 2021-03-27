package ru.boomearo.gamecontrol.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.FluidLevelChangeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.SpongeAbsorbEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().hasPermission("gamecontrol.bypass")) {
            return;
        }
        e.setCancelled(true);
    }
    
    
    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockCookEvent(BlockCookEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockDispenseArmorEvent(BlockDispenseArmorEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockDropItemEvent(BlockDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    
    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockFadeEvent(BlockFadeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockFertilizeEvent(BlockFertilizeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockFormEvent(BlockFormEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockGrowEvent(BlockGrowEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockIgniteEvent(BlockIgniteEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockMultiPlaceEvent(BlockMultiPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().hasPermission("gamecontrol.bypass")) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPistonEvent(BlockPistonExtendEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().hasPermission("gamecontrol.bypass")) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockShearEntityEvent(BlockShearEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockSpreadEvent(BlockSpreadEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityBlockFormEvent(EntityBlockFormEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onFluidLevelChangeEvent(FluidLevelChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onMoistureChangeEvent(MoistureChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onSignChangeEvent(SignChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().hasPermission("gamecontrol.bypass")) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onSpongeAbsorbEvent(SpongeAbsorbEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
}
