package ru.boomearo.gamecontrol.objects;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Класс, представляющий арену, которую нужно регенерировать после запуска плагина
 */
public class StoredRegenArena implements ConfigurationSerializable {

    private final String name;
    private volatile boolean needRegen;
    
    public StoredRegenArena(String name, boolean needRegen) {
        this.name = name;
        this.needRegen = needRegen;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isNeedRegen() {
        return this.needRegen;
    }
    
    public void setNeedRegen(boolean need) {
        this.needRegen = need;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("name", this.name);
        result.put("needRegen", this.needRegen);

        return result;
    }

    public static StoredRegenArena deserialize(Map<String, Object> args) {
        String name = "ar";
        boolean needRegen = true;

        Object n = args.get("name");
        if (n != null) {
            name = (String) n;
        }

        Object nr = args.get("needRegen");
        if (nr != null) {
            needRegen = (boolean) nr;
        }

        return new StoredRegenArena(name, needRegen);
    }
}
