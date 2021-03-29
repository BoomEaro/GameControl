package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Location;
import org.bukkit.World;

public abstract class ClipboardRegenableGameArena extends RegenableGameArena {

    private final Location originCenter;
    
    public ClipboardRegenableGameArena(String name, World world, Location originCenter) {
        super(name, world);
        this.originCenter = originCenter;
    }
    
    public Location getOriginCenter() {
        return this.originCenter;
    }

}
