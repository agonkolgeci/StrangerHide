package fr.jielos.strangerhide.listeners;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.GameState;
import fr.jielos.strangerhide.game.Game.WaitingState;
import fr.jielos.strangerhide.references.Inventories;
import fr.jielos.strangerhide.references.Items;
import fr.jielos.strangerhide.references.Locations;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Map;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Game game = Main.getInstance().getGame();
		final Player player = event.getPlayer();
		
		event.setJoinMessage(null);

		player.setHealth(20); player.setFoodLevel(20);
		player.setLevel(0); player.setExp(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.spigot().respawn();
		player.setGlowing(false);
		for(final PotionEffect potionEffect : player.getActivePotionEffects()) {
			player.removePotionEffect(potionEffect.getType());
		}

		((CraftPlayer) player).getHandle().updateInventory(((CraftPlayer) player).getHandle().activeContainer);

		if(game.getGameState() == GameState.WAITING || game.getGameState() == GameState.LAUNCHING) {
			game.getData().getPlayers().add(player);
			player.setGameMode(GameMode.ADVENTURE);
			
			if(game.getMap() != null) player.teleport(game.getMap().getLobby());
			else player.teleport(Locations.MAP_SELECT_ROOM.getContent());

			player.sendMessage("§c/!\\ Nous vous rappelons d'activer le pack de texture au préalable pour avoir une meilleure expérience de jeu.");
			game.getInstance().getServer().broadcastMessage("§7" + player.getName() + " §evient de rejoindre la partie ! (§b"+game.getData().getPlayers().size()+"/"+game.getConfig().getMaxPlayers()+"§e)");

			for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
				entry.getValue().set("§7Joueurs: §b" + game.getData().getPlayers().size() + "/" + game.getConfig().getMaxPlayers(), 5);
			}

			if(game.getWaitingState() == WaitingState.MAP_SELECT || game.getWaitingState() == WaitingState.TELEPORTING && game.getGameState() != GameState.LAUNCHING) {
				player.getInventory().setItem(Items.MAP_SELECTOR.getSlot(), Items.MAP_SELECTOR.getContent().toItemStack());
				player.getInventory().setItem(Items.JUMP_TELEPORT.getSlot(), Items.JUMP_TELEPORT.getContent().toItemStack());

				if(player.isOp()) {
					player.getInventory().setItem(Items.CONFIGURATION.getSlot(), Items.CONFIGURATION.getContent().toItemStack());
				}

				player.getInventory().setHeldItemSlot(Items.MAP_SELECTOR.getSlot());
				((CraftPlayer) player).getHandle().updateInventory(((CraftPlayer) player).getHandle().activeContainer);

				game.getData().getInventoriesGui().get(Inventories.MAP_SELECTOR).display(player);

				if(game.getWaitingState() != WaitingState.TELEPORTING) {
					if(game.getData().getPlayers().size() >= game.getConfig().getMinPlayers()) game.teleport();
					else player.sendTitle("", "§7En attente de joueurs ..", 20, Integer.MAX_VALUE, 20);
				}
			}
		} else if(game.getGameState() == GameState.PLAYING) {
			game.getData().getSpectators().add(player);
			player.setGameMode(GameMode.SPECTATOR);
			player.teleport(game.getMap().getLobby());
			
			game.getInstance().getServer().broadcastMessage("§8" + player.getName() + " §7vient de rejoindre en tant que Spectateur.");
		}

		game.displayBoard(player);
	}
}
