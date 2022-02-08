package fr.jielos.strangerhide.schedulers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.GameState;
import fr.jielos.strangerhide.references.Teams;
import fr.jielos.strangerhide.utils.Time;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Searching extends BukkitRunnable {

	final Game game;
	
	int seconds;
	
	public Searching(final Game game) {
		this.game = game;
		this.seconds = game.getConfig().getSearchingSeconds();
	}
	
	@Override
	public void run() {
		if(game.getGameState() == GameState.PLAYING) {
			if(seconds <= 0) {
				this.cancel();

				game.end(Teams.HIDER, game.getData().getHiders());
				return;
			}
			
			if(seconds <= 10) {
				for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
					player.sendTitle(ChatColor.GRAY + String.valueOf(seconds), "", 0, 2*20, 0);
				}
			}

			for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Fin de la partie dans §e" + Time.format(seconds) + "§7."));
			}
				
			seconds--;
		}
	}
	
}
