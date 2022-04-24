package ru.boomearo.gamecontrol.objects;

import org.bukkit.entity.Player;

import ru.boomearo.gamecontrol.objects.arena.AbstractGameArena;

/**
 * Базовое представление об игроке, который будет участвовать в любых играх.
 */
public interface IGamePlayer {

    /**
     * @return ник игрока
     */
    public String getName();

    /**
     * @return объект игрока
     * @see Player
     */
    public Player getPlayer();

    /**
     * @return арена, в которой участвует игрок
     * @see AbstractGameArena
     */
    public AbstractGameArena<? extends IGamePlayer> getArena();

}
