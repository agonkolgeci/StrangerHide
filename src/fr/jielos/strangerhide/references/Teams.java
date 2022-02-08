package fr.jielos.strangerhide.references;

import org.bukkit.ChatColor;

public enum Teams {

	HIDER("Hider", "Cacheur", ChatColor.YELLOW, false),
	SEEKER("Seeker", "Chercheur", ChatColor.RED, true);
	
	final String name;
	final String displayName;
	final ChatColor chatColor;
	final boolean nameTagVisibility;
	Teams(final String name, final String displayName, final ChatColor chatColor, final boolean nameTagVisibility) {
		this.name = name;
		this.displayName = displayName;
		this.chatColor = chatColor;
		this.nameTagVisibility = nameTagVisibility;
	}
	
	public String getName() {
		return name;
	}
	public String getDisplayName() {
		return displayName;
	}
	
	public ChatColor getChatColor() {
		return chatColor;
	}
	
	public boolean isNameTagVisibility() {
		return nameTagVisibility;
	}

}