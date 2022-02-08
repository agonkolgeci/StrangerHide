package fr.jielos.strangerhide.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.game.Game;

public class AsyncPlayerChat implements Listener {

	@EventHandler
	public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
		final Game game = Main.getInstance().getGame();
		final Player player = event.getPlayer();
		
		final Scoreboard scoreboard = game.getScoreboard();
		final Team team = scoreboard.getEntryTeam(player.getName());
		
		ChatColor chatColor = null;
		if(team != null) chatColor = team.getColor();
		if(chatColor == null) chatColor = ChatColor.GRAY;
		
		event.setFormat(chatColor + "%1$s §8: §7%2$s");
	}
	
}
