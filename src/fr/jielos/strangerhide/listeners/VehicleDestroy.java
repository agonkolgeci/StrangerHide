package fr.jielos.strangerhide.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class VehicleDestroy implements Listener {
	
	@EventHandler
	public void onVehicleDestroy(final VehicleDestroyEvent event) {
		if(event.getAttacker() instanceof Player) {
			final Player player = (Player) event.getAttacker();
			if(player.getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
		}
	}
	
}
