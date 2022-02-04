package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Material;
import org.bukkit.World;
import ru.boomearo.gamecontrol.objects.IGamePlayer;

/**
 * Абстрактное представление арены, которая может регенерироваться любым способом
 */
public abstract class RegenableGameArena<T extends IGamePlayer> extends AbstractGameArena<T> {

    public RegenableGameArena(String name, World world, Material icon) {
        super(name, world, icon);
    }

}
