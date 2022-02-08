package fr.jielos.strangerhide.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.jielos.strangerhide.game.Game.GameState;

import fr.jielos.strangerhide.game.Game;

import fr.jielos.strangerhide.Main;

public class EntityDamage implements Listener {

	@EventHandler
	public void onEntityDamage(final EntityDamageEvent event) {
		final Game game = Main.getInstance().getGame();

		if(event.getCause() == DamageCause.FALL || event.getCause() == DamageCause.FIRE) event.setCancelled(true);
		if(game.getGameState() != GameState.PLAYING) {
			event.setCancelled(true);
		}
	}
	
}
