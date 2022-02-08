package fr.jielos.strangerhide.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChange implements Listener {

	@EventHandler
	public void onFoodLevelChange(final FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
}
