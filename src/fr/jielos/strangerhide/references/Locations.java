package fr.jielos.strangerhide.references;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Locations {

	MAP_SELECT_ROOM(new Location(Bukkit.getWorlds().get(0), -274, 4.5, 316, 0, 0)),
	JUMP(new Location(Bukkit.getWorlds().get(0), -279.5, 4.5, 354.5, 0, 0)),
	END(new Location(Bukkit.getWorlds().get(0), -252, 15, 347, 0, 0));
	
	final Location content;
	Locations(final Location content) {
		this.content = content;
	}
	
	public Location getContent() {
		return content;
	}
	
}
