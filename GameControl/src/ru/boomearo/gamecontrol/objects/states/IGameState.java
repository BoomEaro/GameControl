package ru.boomearo.gamecontrol.objects.states;

import ru.boomearo.gamecontrol.objects.IGameArena;

public interface IGameState {

    public String getName();
    
    public void initState(IGameArena arena);
    
    public void autoUpdateHandler(IGameArena arena);
}
