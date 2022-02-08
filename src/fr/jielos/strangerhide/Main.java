package fr.jielos.strangerhide;

import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.managers.ListenersManager;
import fr.jielos.strangerhide.managers.TeamsManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	static Main instance;
	public static Main getInstance() {
		return instance;
	}

	Game game;

	@Override
	public void onEnable() {
		instance = this;

		game = new Game(this);
		
		new ListenersManager(this).register();
		new TeamsManager(this).register();
	}

	@Override
	public void onDisable() {
		for(final Player player : getServer().getOnlinePlayers()) {
			player.kickPlayer("§cUn redémarrage du fonctionnement du jeu a été effecuté.\n§cVeuillez vous reconnectez afin de continuer.");
		}
	}

	public Game getGame() {
		return game;
	}
}
