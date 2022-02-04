package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import ru.boomearo.gamecontrol.objects.IGamePlayer;

/**
 * Абстрактное представление арены, в которой более точно описано то, каким образом она должна регенерироваться.
 * Эта абстракция предполагает, что арена является разрушаемой и ее требуется регенерировать соответственно.
 */
public abstract class ClipboardRegenableGameArena<T extends IGamePlayer> extends RegenableGameArena<T> {

    private final Location originCenter;

    public ClipboardRegenableGameArena(String name, World world, Material icon, Location originCenter) {
        super(name, world, icon);
        this.originCenter = originCenter;
    }

    /**
     * @return центр, относительно которого будет вставлен схематик от FastAsyncWorldEdit для регенерации.
     * @see Location
     */
    public Location getOriginCenter() {
        return this.originCenter;
    }

}
