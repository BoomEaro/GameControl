package ru.boomearo.gamecontrol.objects;

import org.bukkit.entity.Player;

import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;

public interface IGamePlayer {

    public String getName();
    public Player getPlayer();
    
    public AbstractGameArena getArena();
}
