package fr.jielos.strangerhide.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

public class PlayerUnleashedEntity implements Listener {

	@EventHandler
	public void onPlayerUnleashedEntity(final PlayerUnleashEntityEvent event) {
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}
	
}