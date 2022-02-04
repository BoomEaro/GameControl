package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Material;
import org.bukkit.World;
import ru.boomearo.gamecontrol.objects.IGamePlayer;
import ru.boomearo.gamecontrol.objects.states.IGameState;

/**
 * Абстрактное представление арены, которая может регенерироваться любым способом
 */
public abstract class RegenableGameArena<T extends IGamePlayer> extends AbstractGameArena<T> {

    public RegenableGameArena(String name, World world, Material icon, IGameState state) {
        super(name, world, icon, state);
    }

}
