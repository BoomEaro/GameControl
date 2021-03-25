package ru.boomearo.gamecontrol.objects.states;

import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;

public interface IGameState {

    public String getName();
    
    public AbstractGameArena getArena();
    
    public void initState();
    
    public void autoUpdateHandler();
}
