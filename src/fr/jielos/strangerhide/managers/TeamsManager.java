package fr.jielos.strangerhide.managers;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.references.Teams;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamsManager {

	final JavaPlugin instance;
	public TeamsManager(final JavaPlugin instance) {
		this.instance = instance;
	}
	
	public void register() {
		final Scoreboard scoreboard = Main.getInstance().getGame().getScoreboard();
		
		for(final Team team : scoreboard.getTeams()) team.unregister();
		for(final Teams team : Teams.values()) {
			if(scoreboard.getTeam(team.getName()) == null) {
				final Team scoreboardTeam = scoreboard.registerNewTeam(team.getName());
				scoreboardTeam.setDisplayName(team.getDisplayName());
				scoreboardTeam.setColor(team.getChatColor());;
				scoreboardTeam.setAllowFriendlyFire(false);

				if(team == Teams.SEEKER && Main.getInstance().getGame().getConfig().isSeekersNameTag()) {
					return;
				} else if(team != Teams.SEEKER) {
					if(team.isNameTagVisibility()) {
						return;
					}
				}

				scoreboardTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
			}
		}
	}
	
}