package fr.jielos.strangerhide.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerItemDrop implements Listener {

	@EventHandler
	public void onItemDrop(final PlayerDropItemEvent event) {
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}
}
