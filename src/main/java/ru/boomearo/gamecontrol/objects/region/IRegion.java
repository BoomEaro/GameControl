package ru.boomearo.gamecontrol.objects.region;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Базовое представление региона, который используется в аренах для установки границ
 */
public interface IRegion {

    public boolean isInRegionPoint(Location loc);
    public boolean isInRegionPoint(World world, double x, double y, double z);
    
    public List<ChunkCords> getAllChunks();
    
    public static class ChunkCords {
        private final int x;
        private final int z;
        
        public ChunkCords(int x, int z) {
            this.x = x;
            this.z = z;
        }
        
        public int getX() {
            return this.x;
        }
        
        public int getZ() {
            return this.z;
        }
    }
}
