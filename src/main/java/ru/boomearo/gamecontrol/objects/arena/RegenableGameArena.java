package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Material;
import org.bukkit.World;

/**
 * Абстрактное представление арены, которая может регенерироваться любым способом
 */
public abstract class RegenableGameArena extends AbstractGameArena {

    public RegenableGameArena(String name, World world, Material icon) {
        super(name, world, icon);
    }

}
