package fr.jielos.strangerhide.listeners;

import fr.jielos.strangerhide.references.Inventories;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.GameState;
import fr.jielos.strangerhide.references.Teams;

import java.util.Map;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final Game game = Main.getInstance().getGame();
		final Player player = event.getPlayer();
		
		event.setQuitMessage(null);
		if(Netherboard.instance().getBoards().containsKey(player)) {
			Netherboard.instance().getBoard(player).delete();
		}

		if(game.getGameState() != GameState.PLAYING) {
			if(game.getData().getPlayers().contains(player)) {
				game.getData().getPlayers().remove(player);
				if(game.getConfig().getSeekersCount() > (game.getData().getPlayers().size() - 1)) {
					game.getConfig().setSeekersCount(game.getConfig().getSeekersCount()-1);

					for(final Player gamePlayer : game.getData().getPlayers()) {
						if(gamePlayer.getOpenInventory().getTitle().equals(Inventories.CONFIGURATION.getTitle())) {
							game.getData().getInventoriesGui().get(Inventories.CONFIGURATION).update(gamePlayer);
						}
					}
				}

				game.getInstance().getServer().broadcastMessage("§7" + player.getName() + " §evient de quitté la partie ! (§b"+game.getData().getPlayers().size()+"/"+game.getConfig().getMaxPlayers()+"§e)");
				for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
					entry.getValue().set("§7Joueurs: §b" + game.getData().getPlayers().size() + "/" + game.getConfig().getMaxPlayers(), 5);
				}
			}
		} else {
			if(game.getData().getPlayers().contains(player)) {
				game.getData().getPlayers().remove(player);
				for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
					entry.getValue().set("§7En vie: §a" + game.getData().getPlayers().size() + "/" + game.getConfig().getMaxPlayers(), 9);
				}

				game.getData().getHiders().remove(player);
				game.getData().getSeekers().remove(player);

				final Scoreboard scoreboard = game.getScoreboard();
				final Team team = scoreboard.getEntryTeam(player.getName());

				if(team != null) game.getInstance().getServer().broadcastMessage(team.getColor() + player.getDisplayName() + "§7 s'est déconnecté.");
				
				if(game.getData().getHiders().size() <= 0) {
					game.end(Teams.SEEKER, game.getData().getSeekers());
				} else if(game.getData().getSeekers().size() <= 0) {
					game.end(Teams.HIDER, game.getData().getHiders());
				}

				if(team != null) team.removeEntry(player.getName());
			} else if(game.getData().getSpectators().contains(player)) {
				game.getData().getSpectators().remove(player);
				game.getInstance().getServer().broadcastMessage("§8" + player.getName() + " §7vient de quitter les Spectateurs.");
			}
		}
	}
	
}
