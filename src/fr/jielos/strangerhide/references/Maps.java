package fr.jielos.strangerhide.references;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Maps {
	
	STRANGER_THINGS_S1(0, "Stranger Things S1", "ST S1", new String[]{"Le 6 novembre 1983, à Hawkins dans l'Indiana,", "une créature s'échappe d'un laboratoire du", "Département de l'énergie, emportant un", "scientifique dans sa fuite. Plus tard,", "Will Byers, un garçon de 12 ans,", "rentre chez lui après une longue partie de", "Donjons et Dragons et se fait", "attaquer par une créature."}, new Location(Bukkit.getWorlds().get(0), -134.5, 18.5, 140.5, 180, 0), new Location(Bukkit.getWorlds().get(0), -134.5, 18.5, 144.5, 180, 0), new Location(Bukkit.getWorlds().get(0), -185.5, 19.5, 132.5, -90, 0), new Location[]{new Location(Bukkit.getWorlds().get(0), -202.5, 36, 107.5, -90, 35), new Location(Bukkit.getWorlds().get(0), -125.5, 41, 69.5, 90, 35), new Location(Bukkit.getWorlds().get(0), -145.5, 37, -26.5, 0, 35)}),
	STRANGER_THINGS_S2(1, "Stranger Things S2", "ST S2", new String[]{"En 1984, à Hawkins dans l’Indiana, un an a passé", "depuis l'attaque du Démogorgon et la disparition", "d'Onze. Will Byers a des visions du", "Monde à l'envers et de son maître, une", "créature gigantesque et tentaculaire.", "Plusieurs signes indiquent que les monstres", "vont franchir le portail et revenir sur la ville. "}, new Location(Bukkit.getWorlds().get(0), 157.5, 17.5, 140.5, 180, 0), new Location(Bukkit.getWorlds().get(0), 157.5, 17.5, 146.5, 180, 0), new Location(Bukkit.getWorlds().get(0), 107.5, 19.5, 132.5, -90, 0), new Location[]{new Location(Bukkit.getWorlds().get(0), 173.5, 39, 141.5, 135, 35), new Location(Bukkit.getWorlds().get(0), 122.5, 38, 76.5, -45, 60), new Location(Bukkit.getWorlds().get(0), 65.5, 41, 141.5, -135, 45)}),
	STRANGER_THINGS_S3(2, "Stranger Things S3", "ST S3", new String[]{"Nous sommes en 1985 à Hawkins, dans l'Indiana,", "et l'été se réchauffe. L'école est terminée, il", "y a un tout nouveau centre commercial en", "ville et l'équipage Hawkins est sur le", "point de devenir adulte. La romance fleurit", "et complique la dynamique du groupe, et", "ils devront trouver comment grandir", "sans se séparer ..."}, new Location(Bukkit.getWorlds().get(0), 109.5, 20.5, 299.5, 0, 0), new Location(Bukkit.getWorlds().get(0), 91.5, 12.5, 295.5, 180, 0), new Location(Bukkit.getWorlds().get(0), 166.5, 19.5, 362.5, -90, 0), new Location[]{new Location(Bukkit.getWorlds().get(0), 125.5, 28, 375.5, -150, 30), new Location(Bukkit.getWorlds().get(0), 137.5, 27, 316.5, 180, 35), new Location(Bukkit.getWorlds().get(0), 133.5, 35, 266.5, 45, 45)}),

	RANDOM(8, "Aléatoire", "Aléatoire", new String[]{"Un tirage au sort des maps", "sera effectué lors du lancement", "de la partie !"}, null, null, null, null);
	
	final int slot;
	final String name;
	final String shortName;
	final String[] synopsis;
	final Location lobby;
	final Location cage;
	final Location bus;
	final Location[] cameras;
	Maps(final int slot, final String name, final String shortName, final String[] synopsis, final Location lobby, final Location cage, final Location bus, final Location[] cameras) {
		this.slot = slot;
		this.name = name;
		this.shortName = shortName;
		this.synopsis = synopsis;
		this.lobby = lobby;
		this.cage = cage;
		this.bus = bus;
		this.cameras = cameras;
	}
	
	public int getSlot() {
		return slot;
	}

	public String getName() {
		return name;
	}
	public String getShortName() {
		return shortName;
	}

	public String[] getSynopsis() {
		return synopsis;
	}
	
	public Location getLobby() {
		return lobby;
	}
	public Location getCage() {
		return cage;
	}
	public Location getBus() {
		return bus;
	}

	public Location[] getCameras() {
		return cameras;
	}

	public static Maps getMapBySlot(final int slot) {
		for(Maps map : values()) {
			if(map.getSlot() == slot) {
				return map;
			}
		}
		
		return null;
	}
	
}