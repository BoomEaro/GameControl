package ru.boomearo.gamecontrol.objects.states;

import ru.boomearo.gamecontrol.objects.IGameArena;

public interface IGameState {

    public String getName();
    
    public IGameArena getArena();
    
    public void initState();
    
    public void autoUpdateHandler();
}
