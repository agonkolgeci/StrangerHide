package fr.jielos.strangerhide.game;

import fr.jielos.strangerhide.references.Roles;

public class Config {

	final Game game;

	int minPlayers = (int) DefaultConfig.MIN_PLAYERS.getValue();
	int maxPlayers = (int) DefaultConfig.MAX_PLAYERS.getValue();

	boolean rolesEnabled = (boolean) DefaultConfig.ROLES_ENABLED.getValue();

	int seekersCount = (int) DefaultConfig.SEEKERS_COUNT.getValue();
	boolean seekersNameTag = (boolean) DefaultConfig.SEEKERS_NAME_TAG.getValue();
	boolean multipleRoles = (boolean) DefaultConfig.MULTIPLE_ROLES.getValue();
	boolean spectatorMode = (boolean) DefaultConfig.SPECTATOR_MODE.getValue();
	boolean pvp = (boolean) DefaultConfig.PVP.getValue();
	boolean camerasEquipment = (boolean) DefaultConfig.CAMERAS_EQUIPMENT.getValue();

	int countingSeconds = (int) DefaultConfig.COUNTING_SECONDS.getValue();
	int searchingSeconds = (int) DefaultConfig.SEARCHING_SECONDS.getValue();
	
	int teleportingSeconds = 30;
	int launchingSeconds = 15;
	int endingSeconds = 15;

	public Config(final Game game) {
		this.game = game;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = (Math.max(minPlayers, 2));
	}
	public int getMinPlayers() {
		return (Math.max(minPlayers, 2));
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setRolesEnabled(boolean rolesEnabled) {
		this.rolesEnabled = rolesEnabled;
	}
	public boolean isRolesEnabled() {
		return rolesEnabled;
	}

	public void setSeekersCount(int seekersCount) {
		this.seekersCount = (Math.max(seekersCount, 1));;
	}
	public int getSeekersCount() {
		return seekersCount;
	}

	public boolean isSeekersNameTag() {
		return seekersNameTag;
	}
	public void setSeekersNameTag(boolean seekersNameTag) {
		this.seekersNameTag = seekersNameTag;
	}

	public boolean isMultipleRoles() {
		return multipleRoles;
	}
	public void setMultipleRoles(boolean multipleRoles) {
		this.multipleRoles = multipleRoles;
	}

	public boolean isSpectatorMode() {
		return spectatorMode;
	}
	public void setSpectatorMode(boolean spectatorMode) {
		this.spectatorMode = spectatorMode;
	}

	public boolean isPvp() {
		return pvp;
	}
	public void setPvp(boolean pvp) {
		this.pvp = pvp;
	}

	public boolean isCamerasEquipment() {
		return camerasEquipment;
	}
	public void setCamerasEquipment(boolean camerasEquipment) {
		this.camerasEquipment = camerasEquipment;
	}

	public int getCountingSeconds() {
		return countingSeconds;
	}
	public void setCountingSeconds(int countingSeconds) {
		this.countingSeconds = countingSeconds;
	}

	public int getSearchingSeconds() {
		return searchingSeconds;
	}
	public void setSearchingSeconds(int searchingSeconds) {
		this.searchingSeconds = searchingSeconds;
	}

	public int getTeleportingSeconds() {
		return teleportingSeconds;
	}
	public int getLaunchingSeconds() {
		return launchingSeconds;
	}
	public int getEndingSeconds() {
		return endingSeconds;
	}

	public enum DefaultConfig {
		MIN_PLAYERS(3),
		MAX_PLAYERS(Roles.getRoles().size()),

		ROLES_ENABLED(true),

		SEEKERS_COUNT(1),
		SEEKERS_NAME_TAG(true),
		MULTIPLE_ROLES(false),
		SPECTATOR_MODE(true),
		PVP(true),
		CAMERAS_EQUIPMENT(false),

		COUNTING_SECONDS(60),
		SEARCHING_SECONDS(60*10);

		final Object value;
		DefaultConfig(final Object content) {
			this.value = content;
		}

		public Object getValue() {
			return value;
		}
	}
}
