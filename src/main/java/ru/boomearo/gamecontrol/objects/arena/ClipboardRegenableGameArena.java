package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * Абстрактное представление арены, в которой более точно описано то, каким образом она должна регенерировать.
 * Эта абстракция предполагает, что арена является разрушаемой и ее требуется регенерировать соответственно.
 */
public abstract class ClipboardRegenableGameArena extends RegenableGameArena {

    private final Location originCenter;
    
    public ClipboardRegenableGameArena(String name, World world, Material icon, Location originCenter) {
        super(name, world, icon);
        this.originCenter = originCenter;
    }

    /**
     * @return центр, относительно которого будет вставлен схематик от FastAsyncWorldEdit для регенерации.
     */
    public Location getOriginCenter() {
        return this.originCenter;
    }

}
