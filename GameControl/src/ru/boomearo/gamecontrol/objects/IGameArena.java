package ru.boomearo.gamecontrol.objects;

import java.util.Collection;

import ru.boomearo.gamecontrol.objects.states.IGameState;

public interface IGameArena {

    public String getName();
    
    public IGameManager getManager();
    
    public IGamePlayer getGamePlayer(String name);
    public Collection<? extends IGamePlayer> getAllPlayers();
    
    public IGameState getState();
    
    public void regen();
}
