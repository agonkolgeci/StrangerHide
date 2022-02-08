package fr.jielos.strangerhide.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class HangingBreakByEntity implements Listener {

	@EventHandler
	public void onHangingBreakByEntity(final HangingBreakByEntityEvent event) {
		if(event.getRemover() instanceof Player) {
			final Player player = (Player) event.getRemover();
			if((event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Painting || event.getEntity() instanceof LeashHitch || event.getEntity() instanceof Minecart) && player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			}
		}
		
	}
	
}