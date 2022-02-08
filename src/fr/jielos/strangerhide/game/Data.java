package fr.jielos.strangerhide.game;

import fr.jielos.strangerhide.gui.Gui;
import fr.jielos.strangerhide.references.Inventories;
import fr.jielos.strangerhide.references.Maps;
import fr.jielos.strangerhide.references.Roles;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Data {

	final Game game;
	
	List<Player> players;
	List<Player> spectators;

	List<Player> hiders;
	List<Player> seekers;

	Map<Inventories, Gui> inventoriesGui;

	Map<UUID, Maps> playersMapsVotes;
	Map<Maps, Integer> mapsVotes;

	Map<Player, Roles> playersRoles;
	Map<Player, Location> playersCameras;

	public Data(final Game game) {
		this.game = game;
		
		this.players = new ArrayList<>();
		this.spectators = new ArrayList<>();
		
		this.hiders = new ArrayList<>();
		this.seekers = new ArrayList<>();

		this.inventoriesGui = new HashMap<>();
		for(final Inventories inventory : Inventories.values()) {
			inventoriesGui.put(inventory, new Gui(game, inventory));
		}

		this.playersMapsVotes = new HashMap<>();
		this.mapsVotes = new HashMap<>();
		for(final Maps map : Maps.values()) {
			this.mapsVotes.put(map, 0);
		}

		this.playersRoles = new HashMap<>();
		this.playersCameras = new HashMap<>();
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Player> getSpectators() {
		return spectators;
	}

	public List<Player> getHiders() {
		return hiders;
	}
	
	public List<Player> getSeekers() {
		return seekers;
	}

	public Map<Inventories, Gui> getInventoriesGui() {
		return inventoriesGui;
	}

	public Map<UUID, Maps> getPlayersMapsVotes() {
		return playersMapsVotes;
	}

	public Map<Maps, Integer> getMapsVotes() {
		return mapsVotes;
	}

	public Map<Player, Roles> getPlayersRoles() {
		return playersRoles;
	}

	public Map<Player, Location> getPlayersCameras() {
		return playersCameras;
	}
}
