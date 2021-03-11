package ru.boomearo.gamecontrol.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

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
    
}
