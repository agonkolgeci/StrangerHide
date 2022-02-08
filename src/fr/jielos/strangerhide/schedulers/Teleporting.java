package fr.jielos.strangerhide.schedulers;

import java.util.*;
import java.util.stream.Collectors;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.WaitingState;
import fr.jielos.strangerhide.references.Maps;
import fr.jielos.strangerhide.utils.Time;
import net.md_5.bungee.api.ChatColor;

public class Teleporting extends BukkitRunnable {

	final Game game;
	
	int seconds;
	
	public Teleporting(final Game game) {
		this.game = game;
		this.seconds = game.getConfig().getTeleportingSeconds();
	}
	
	@Override
	public void run() {
		if(game.getData().getPlayers().size() >= game.getConfig().getMinPlayers()) {
			if(seconds <= 0) {
				this.cancel();

				game.getInstance().getServer().broadcastMessage("§7Sélection de la map parmis les votes en cours ...");

				final int highVote = Collections.max(new ArrayList<>(game.getData().getMapsVotes().values()));
				final List<Maps> entries = game.getData().getMapsVotes().entrySet().stream().filter(e -> e.getValue() == highVote).map(Map.Entry::getKey).collect(Collectors.toList());

				boolean randomMap = false;
				if(entries.size() == 1) {
					final Maps map = entries.get(0);

					if(map == Maps.RANDOM) randomMap = true;
					else game.setMap(map);
				} else if(entries.size() > 1) randomMap = true;

				if(randomMap) {
					final List<Maps> maps = Arrays.stream(Maps.values()).filter(e -> e != Maps.RANDOM).collect(Collectors.toList());
					int pick = new Random().nextInt(maps.size());

					game.setMap(maps.get(pick));
				}
				
				game.getInstance().getServer().broadcastMessage(" \n§aMap sélectionnée §8: §6§l" + game.getMap().getName() + "\n §7§o" + String.join(" ", game.getMap().getSynopsis()) + " \n ");

				for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
					entry.getValue().set("§7Map sélectionnée: §6" + game.getMap().getShortName(), 4);
					entry.getValue().set("§aTéléportation ...", 3);
				}

				game.launch();
				
				return;
			}

			for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
				entry.getValue().set("§8⋆ §7Téléportation: §e" + Time.format(seconds), 3);
			}
			
			for(final Player player : game.getData().getPlayers()) {
				if(seconds % 10 == 0 || seconds <= 5) {
					player.sendMessage("§eTéléportation vers la map dans §c" + (seconds <= 10 ? ChatColor.RED : (seconds <= 20 ? ChatColor.GOLD : ChatColor.GREEN)) + seconds + " §eseconde" + (seconds > 1 ? "s" : "") + " !");
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, ((float) (game.getConfig().getTeleportingSeconds()-seconds)), 1F);
				}
				
				player.sendTitle("", "§7Téléportation dans §e" + Time.format(seconds) + "§7.", 0, 2*20, 0);
			}
			
			seconds--;
		} else {
			this.cancel();

			game.setWaitingState(WaitingState.MAP_SELECT);
			game.getInstance().getServer().broadcastMessage("§cTéléportation vers la map annulée, il n'y a plus assez de joueurs.");

			for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
				entry.getValue().set("§cEn attente de joueurs", 3);
			}

			for(final Player player : game.getData().getPlayers()) {
				player.sendTitle("", "§cTéléportation annulée.", 0, 3*20, 0);
			}
		}
	}
	
}
