package ru.boomearo.gamecontrol.objects.region;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.sk89q.worldedit.math.BlockVector3;

public class CuboidRegion implements IRegion, ConfigurationSerializable {

    private final Location loc1;
    private final Location loc2;

    public CuboidRegion(Location loc1, Location loc2) {
        Location[] loc = fixRegion(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), loc1.getWorld());
        this.loc1 = loc[0];
        this.loc2 = loc[1];
    }

    public CuboidRegion(BlockVector3 loc1, BlockVector3 loc2, World world) {
        Location[] loc = fixRegion(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), world);
        this.loc1 = loc[0];
        this.loc2 = loc[1];
    }
    
    private Location[] fixRegion(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax, World world) {
        Location[] loc = new Location[2];
                
        double xMin2 = (xMin < xMax ? xMin : xMax);
        double yMin2 = (yMin < yMax ? yMin : yMax);
        double zMin2 = (zMin < zMax ? zMin : zMax);

        loc[0] = new Location(world, xMin2, yMin2, zMin2);

        double xMax2 = (xMin > xMax ? xMin : xMax);
        double yMax2 = (yMin > yMax ? yMin : yMax);
        double zMax2 = (zMin > zMax ? zMin : zMax);

        loc[1] = new Location(world, xMax2, yMax2, zMax2);
        
        return loc;
    }

    public Location getLocationFirst() {
        return this.loc1.clone();
    }

    public Location getLocationSecond() {
        return this.loc2.clone();
    }

    @Override
    public boolean isInRegionPoint(Location loc) {
        return isInRegionPoint(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
    }
    
    @Override
    public boolean isInRegionPoint(World world, double x, double y, double z) {
        if (!world.getName().equals(this.loc1.getWorld().getName())) {
            return false;
        }

        double xLocIn = x;
        double yLocIn = y;
        double zLocIn = z;

        double Xl = this.loc1.getX();
        double Yl = this.loc1.getY();
        double Zl = this.loc1.getZ();

        double Xr = this.loc2.getX();
        double Yr = this.loc2.getY();
        double Zr = this.loc2.getZ();

        if ((xLocIn >= Xl) && (xLocIn <= Xr + 1.0D) && (yLocIn >= Yl) && (yLocIn <= Yr + 1.0D) && (zLocIn >= Zl) && (zLocIn <= Zr + 1.0D)) {
            return true;
        }

        return false;
    }

    @Override
    public List<ChunkCords> getAllChunks() {
        List<ChunkCords> chunks = new ArrayList<ChunkCords>();
        for (int x = (this.loc1.getBlockX() >> 4); x <= (this.loc2.getBlockX() >> 4); x++) {
            for (int z = (this.loc1.getBlockZ() >> 4); z <= (this.loc2.getBlockZ() >> 4); z++) {
                chunks.add(new ChunkCords(x, z));
            }
        }
        return chunks;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("first", this.loc1);
        result.put("second", this.loc2);

        return result;
    }

    public static CuboidRegion deserialize(Map<String, Object> args) {
        Location loc1 = null;
        Location loc2 = null;

        Object fir = args.get("first");
        if (fir != null) {
            loc1 = (Location) fir;
        }

        Object sec = args.get("second");
        if (sec != null) {
            loc2 = (Location) sec;
        }

        return new CuboidRegion(loc1, loc2);
    }
    
}
