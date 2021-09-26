package ru.boomearo.gamecontrol.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Класс, представляющий игру, чьи арены требуется регенерировать когда плагин только запускается.
 */
public class StoredRegenGame implements ConfigurationSerializable {

    private final String name;
    
    private final ConcurrentMap<String, StoredRegenArena> arenas;
    
    public StoredRegenGame(String name, ConcurrentMap<String, StoredRegenArena> arenas) {
        this.name = name;
        this.arenas = arenas;
    }

    public String getName() {
        return this.name;
    }
    
    public StoredRegenArena getRegenArena(String name) {
        return this.arenas.get(name);
    }
    
    public Collection<StoredRegenArena> getAllArenas() {
        return this.arenas.values();
    }
    
    public void addRegenArena(StoredRegenArena arena) {
        this.arenas.put(arena.getName(), arena);
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("name", this.name);
        result.put("arenas", new ArrayList<>(this.arenas.values()));

        return result;
    }

    @SuppressWarnings("unchecked")
    public static StoredRegenGame deserialize(Map<String, Object> args) {
        String name = "ga";
        ConcurrentMap<String, StoredRegenArena> arenas = new ConcurrentHashMap<>();

        Object n = args.get("name");
        if (n != null) {
            name = (String) n;
        }

        List<StoredRegenArena> rrr = new ArrayList<>();
        Object ar = args.get("arenas");
        if (ar != null) {
            rrr = (List<StoredRegenArena>) ar;
        }

        for (StoredRegenArena aaa : rrr) {
            arenas.put(aaa.getName(), aaa);
        }
        
        return new StoredRegenGame(name, arenas);
    }
}
