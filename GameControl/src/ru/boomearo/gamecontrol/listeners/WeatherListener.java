package ru.boomearo.gamecontrol.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
    }
}
