package ru.boomearo.gamecontrol.objects;

import org.bukkit.entity.Player;

public interface IGamePlayer {

    public String getName();
    public Player getPlayer();
    
    public IGameArena getArena();
}
