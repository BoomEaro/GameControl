package ru.boomearo.gamecontrol.objects;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.boomearo.gamecontrol.exceptions.ConsoleGameException;
import ru.boomearo.gamecontrol.exceptions.PlayerGameException;
import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;
import ru.boomearo.gamecontrol.objects.statistics.IStatisticsManager;

//Используется для подключаемых мини игр, чтобы легче определить действия
public interface IGameManager {

    public String getGameName();
    public String getGameDisplayName();
    
    public JavaPlugin getPlugin();
    
    //Возвращает игрока если удалось подключиться к игре.
    public IGamePlayer join(Player pl, String arena) throws ConsoleGameException, PlayerGameException;
    
    public void leave(Player pl) throws ConsoleGameException, PlayerGameException;
    
    public IGamePlayer getGamePlayer(String name);
    public Collection<? extends IGamePlayer> getAllPlayers();
    
    public AbstractGameArena getGameArena(String name);
    public Collection<? extends AbstractGameArena> getAllArenas();
    
    public IStatisticsManager getStatisticManager();
}
