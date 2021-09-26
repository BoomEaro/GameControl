package ru.boomearo.gamecontrol.objects.arena;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.World;

import ru.boomearo.gamecontrol.objects.IGameManager;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.states.IGameState;

/**
 * Абстрактное представление арены, в которой могут быть игроки и свои состояния активности
 */
public abstract class AbstractGameArena {

    private final String name;
    private final World world;

    private final Material icon;

    public AbstractGameArena(String name, World world, Material icon) {
        this.name = name;
        this.world = world;
        this.icon = icon;
    }

    /**
     * @return имя арены
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return мир арены
     * @see World
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * @return иконка арены (обычно используется для меню)
     */
    public Material getIcon() {
        return this.icon;
    }

    /**
     * @return игра, в которой находится эта арена
     */
    public abstract IGameManager getManager();

    /**
     * @return игрок, который находится в этой арене
     */
    public abstract IGamePlayer getGamePlayer(String name);

    /**
     * @return коллекция, содержащая всех игроков этой арены
     */
    public abstract Collection<? extends IGamePlayer> getAllPlayers();


    /**
     * @return минимальное количество игроков для старта в игре
     */
    public abstract int getMinPlayers();

    /**
     * @return максимальное количество игроков которое может быть одновременно в этой арене
     */
    public abstract int getMaxPlayers();

    /**
     * @return состояния арены
     * @see IGameState
     */
    public abstract IGameState getState();

}
