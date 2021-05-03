package ru.boomearo.gamecontrol.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class RegenGame implements ConfigurationSerializable {

    private final String name;
    
    private final ConcurrentMap<String, RegenArena> arenas;
    
    public RegenGame(String name, ConcurrentMap<String, RegenArena> arenas) {
        this.name = name;
        this.arenas = arenas;
    }
    
    public String getName() {
        return this.name;
    }
    
    public RegenArena getRegenArena(String name) {
        return this.arenas.get(name);
    }
    
    public Collection<RegenArena> getAllArenas() {
        return this.arenas.values();
    }
    
    public void addRegenArena(RegenArena arena) {
        this.arenas.put(arena.getName(), arena);
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("name", this.name);
        result.put("arenas", new ArrayList<RegenArena>(this.arenas.values()));

        return result;
    }

    @SuppressWarnings("unchecked")
    public static RegenGame deserialize(Map<String, Object> args) {
        String name = "ga";
        ConcurrentMap<String, RegenArena> arenas = new ConcurrentHashMap<String, RegenArena>();

        Object n = args.get("name");
        if (n != null) {
            name = (String) n;
        }

        List<RegenArena> rrr = new ArrayList<RegenArena>();
        Object ar = args.get("arenas");
        if (ar != null) {
            rrr = (List<RegenArena>) ar;
        }

        for (RegenArena aaa : rrr) {
            arenas.put(aaa.getName(), aaa);
        }
        
        return new RegenGame(name, arenas);
    }
}
