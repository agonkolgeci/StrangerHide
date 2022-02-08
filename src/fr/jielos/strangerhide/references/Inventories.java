package fr.jielos.strangerhide.references;

public enum Inventories {

	MAP_SELECTOR("§8Maps disponibles", 9),

	CONFIGURATION("§8Configuration de la partie", 9*3),
	ROLES_COMPOSITION("§8Composition des rôles", 9*3);
	
	final String title;
	final int size;
	Inventories(final String title, final int size) {
		this.title = title;
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public int getSize() {
		return size;
	}
}
