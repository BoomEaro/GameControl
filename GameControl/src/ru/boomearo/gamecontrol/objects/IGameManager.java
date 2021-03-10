package ru.boomearo.gamecontrol.objects;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.exceptions.PlayerGameException;

//Используется для подключаемых мини игр, чтобы легче определить действия
public interface IGameManager {

    public String getGameName();
    public String getGameDisplayName();
    
    public JavaPlugin getPlugin();
    
    //Возвращает игрока если удалось подключиться к игре.
    public IGamePlayer join(Player pl, String arena) throws ConsoleGameException, PlayerGameException;
    
    public void leave(Player pl) throws ConsoleGameException, PlayerGameException;
    
    public IGamePlayer getGamePlayer(String name);
    public Collection<IGamePlayer> getAllPlayers();
    
    public IGameArena getGameArena(String name);
    public Collection<IGameArena> getAllArenas();
}
