package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Material;
import org.bukkit.World;

public abstract class RegenableGameArena extends AbstractGameArena {

    public RegenableGameArena(String name, World world, Material icon) {
        super(name, world, icon);
    }

}
