package ru.boomearo.gamecontrol.objects.arena;

import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.extent.clipboard.Clipboard;

public abstract class ClipboardRegenableGameArena extends RegenableGameArena {

    private final Clipboard clipboard;
    private final Location originCenter;
    
    public ClipboardRegenableGameArena(String name, World world, Clipboard clipboard, Location originCenter) {
        super(name, world);
        this.clipboard = clipboard;
        this.originCenter = originCenter;
    }
    
    public Clipboard getClipboard() {
        return this.clipboard;
    }
    
    public Location getOriginCenter() {
        return this.originCenter;
    }

}
