package ru.boomearo.gamecontrol.objects.arena;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.World;

import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.states.IGameState;

public abstract class AbstractGameArena {

    private final String name;
    private final World world;
    
    private final Material icon;
    
    public AbstractGameArena(String name, World world, Material icon) {
        this.name = name;
        this.world = world;
        this.icon = icon;
    }
    
    public String getName() {
        return this.name;
    }
    
    public World getWorld() {
        return this.world;
    }
    
    public Material getIcon() {
        return this.icon;
    }
    
    public abstract IGameManager getManager();
    
    public abstract IGamePlayer getGamePlayer(String name);
    public abstract Collection<? extends IGamePlayer> getAllPlayers();
    
    
    public abstract int getMinPlayers();
    public abstract int getMaxPlayers();
    
    public abstract IGameState getState();
    
}
