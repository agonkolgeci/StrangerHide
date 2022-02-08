package fr.jielos.strangerhide.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onWeatherChange(final CreatureSpawnEvent event) {
		event.setCancelled(true);
	}
	
}
