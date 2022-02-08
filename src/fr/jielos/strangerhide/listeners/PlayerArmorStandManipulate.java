package fr.jielos.strangerhide.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class PlayerArmorStandManipulate implements Listener {

	@EventHandler
	public void onPlayerArmorStandManipulate(final PlayerArmorStandManipulateEvent event) {
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}
	
}
