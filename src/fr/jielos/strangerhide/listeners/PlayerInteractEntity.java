package fr.jielos.strangerhide.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {

	@EventHandler
    public void onPlayerInteract(final PlayerInteractEntityEvent event) {
		if(event.getRightClicked() instanceof ItemFrame && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
    }
	
}