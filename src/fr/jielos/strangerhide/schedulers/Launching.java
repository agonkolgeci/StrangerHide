package fr.jielos.strangerhide.schedulers;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.utils.Time;
import net.md_5.bungee.api.ChatColor;

import java.util.Map;

public class Launching extends BukkitRunnable {

	final Game game;
	
	int seconds;
	
	public Launching(final Game game) {
		this.game = game;
		this.seconds = game.getConfig().getLaunchingSeconds();
	}
	
	@Override
	public void run() {
		if(game.getData().getPlayers().size() >= game.getConfig().getSeekersCount()+1) {
			if(seconds <= 0) {
				this.cancel();

				for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
					entry.getValue().set("§aLancement ...", 3);
				}

				game.start();

				return;
			}

			for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
				entry.getValue().set("§8⋆ §7Lancement: §e" + Time.format(seconds), 3);
			}

			for(final Player player : game.getData().getPlayers()) {
				if(seconds % 5 == 0 || seconds <= 5) {
					player.sendMessage("§eDébut de la partie dans §c" + (seconds <= 10 ? ChatColor.RED : (seconds <= 20 ? ChatColor.GOLD : ChatColor.GREEN)) + seconds + " §eseconde" + (seconds > 1 ? "s" : "") + " !");
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, ((float) (game.getConfig().getLaunchingSeconds()-seconds)), 1F);
				}
				
				player.sendTitle("", "§7Début de la partie dans §e" + Time.format(seconds) + "§7.", 0, 2*20, 0);
			}
			
			seconds--;
		} else {
			this.cancel();

			game.getInstance().getServer().broadcastMessage("§cLancement de la partie annulé, il n'y a plus assez de joueurs.");
			for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
				player.kickPlayer("§cLancement de la partie annulé, il n'y a plus assez de joueurs.\n§cIl se trouve que vous n'étiez plus assez dans la partie pour que celle-ci commence, malheureusement celle-ci ne peux pas démarrer.");
			}
			
			game.getInstance().getServer().reload();
		}
	}
	
}
