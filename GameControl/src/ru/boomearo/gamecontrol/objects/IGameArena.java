package ru.boomearo.gamecontrol.objects;

import java.util.Collection;

public interface IGameArena {

    public String getName();
    
    public IGameManager getManager();
    
    public IGamePlayer getGamePlayer(String name);
    public Collection<IGamePlayer> getAllPlayers();
}
