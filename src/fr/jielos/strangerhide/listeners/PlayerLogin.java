package fr.jielos.strangerhide.listeners;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLogin implements Listener {

	@EventHandler
	public void onPlayerLogin(final PlayerLoginEvent event) {
		final Game game = Main.getInstance().getGame();

		if (game.getGameState() == GameState.ENDING) {
			event.disallow(Result.KICK_OTHER, "§cImpossible de rejoindre la partie.\n§cVeuillez patientez, la partie est en cours de re-chargement.");
		} else if (game.getGameState() == GameState.WAITING || game.getGameState() == GameState.LAUNCHING) {
			if (game.getData().getPlayers().size() >= game.getConfig().getMaxPlayers()) {
				event.disallow(Result.KICK_OTHER, "§cImpossible de rejoindre la partie.\n§cLa partie est pleine !");
			}
		}
	}
	
}
