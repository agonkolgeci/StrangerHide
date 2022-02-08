package fr.jielos.strangerhide.game;

import fr.jielos.strangerhide.components.ItemRole;
import fr.jielos.strangerhide.references.*;
import fr.jielos.strangerhide.schedulers.Counting;
import fr.jielos.strangerhide.schedulers.Launching;
import fr.jielos.strangerhide.schedulers.Teleporting;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

	final JavaPlugin instance;

	Scoreboard scoreboard;

	GameState gameState;
	WaitingState waitingState;

	Maps map;

	Teams winner;
	
	Data data;
	Config config;
	
	public Game(final JavaPlugin instance) {
		this.instance = instance;
		this.scoreboard = instance.getServer().getScoreboardManager().getMainScoreboard();

		this.gameState = GameState.WAITING;
		this.waitingState = WaitingState.MAP_SELECT;

		this.data = new Data(this);
		this.config = new Config(this);
	}

	public void displayBoard(final Player player) {
		final BPlayerBoard board = Netherboard.instance().createBoard(player, scoreboard, Boards.MAIN.getDisplayName());
		board.clear();

		switch (gameState) {
			case WAITING: {
				board.set("§1", 6);
				board.set("§7Joueurs: §b" + data.getPlayers().size() + "/" + config.getMaxPlayers(), 5);
				board.set("§7Votre vote: §a" + (data.getPlayersMapsVotes().containsKey(player.getUniqueId()) ? data.getPlayersMapsVotes().get(player.getUniqueId()).getShortName() : "§3Aucun"), 4);
				board.set((waitingState != WaitingState.TELEPORTING ? "§cEn attente de joueurs" : "§4Erreur"), 3);
				board.set("§0", 2);
				board.set(Boards.MAIN.getFooter(), 1);

				break;
			}

			case LAUNCHING: {
				board.set("§1", 6);
				board.set("§7Joueurs: §b" + data.getPlayers().size() + "/" + config.getMaxPlayers(), 5);
				board.set("§7Map sélectionnée: §6" + map.getShortName(), 4);
				board.set("§8⋆ §aTéléportation ...", 3);
				board.set("§0", 2);
				board.set(Boards.MAIN.getFooter(), 1);

				break;
			}

			case PLAYING: {
				final Team team = scoreboard.getEntryTeam(player.getName());
				final Roles role = data.getPlayersRoles().get(player);

				board.set("§2", 10);
				board.set("§7En vie: §a" + data.getHiders().size() + " joueur" + (data.getHiders().size() > 1 ? "s" : ""), 9);

				if(config.isRolesEnabled() && !data.getSpectators().contains(player)) {
					board.set("§8⋆ §7Rôle: " + (team != null ? team.getColor() : ChatColor.GRAY) + (role != null ? (data.getHiders().contains(player) ? role.getDisplayName() : "§eAttribution..") : "§fAucun"), 8);
					board.set("§1", 7);
				}

				board.set("§0", 2);
				board.set(Boards.MAIN.getFooter(), 1);

				if(config.isRolesEnabled() && role != null) {
					if(data.getHiders().contains(player)) {
						for(final ItemRole itemRole : role.getItems()) {
							if(!itemRole.getItemBuilder().toItemStack().getItemMeta().getDisplayName().contains("Capacité")) return;

							final int index = ArrayUtils.indexOf(role.getItems(), itemRole);
							board.set("§8- §7Capacité #" + (index+1) + ": §aDisponible", (index+3));
						}
					} else if(data.getSeekers().contains(player)) {
						board.set("§cVotre role sera attribué", 6);
						board.set("§cà la fin du compte à rebours", 5);
					}
				} else if(data.getSpectators().contains(player)) {
					board.set("§8⋆ §7Mode Spectateur", 8);
				} else if(config.isRolesEnabled() && role == null) {
					board.set("§cAucunes capacités", 6);
				} else if(!config.isRolesEnabled()) {
					board.set("§3Partie classique", 6);
				}

				break;
			}

			case ENDING: {
				board.setAll(
						"§2",
						"§7Gagnants: §r" + winner.getChatColor() + winner.getDisplayName()+"s",
						"§1",
						"§8⋆ §7Félicitations à l'équipe",
						"§8⋆ §7qui remporte la partie !",
						"§0",
						Boards.MAIN.getFooter()
				);
			}
		}
	}

	public void teleport() {
		this.waitingState = WaitingState.TELEPORTING;
		
		new Teleporting(this).runTaskTimer(instance, 0, 20);
	}
	
	public void launch() {
		this.gameState = GameState.LAUNCHING;

		for(final Player player : data.getPlayers()) {
			player.getInventory().clear();
			player.teleport(map.getLobby());
		}

		instance.getServer().broadcastMessage("§eAssignation des rôles / équipes en cours, début de la partie dans quelques secondes..");
		new Launching(getGame()).runTaskTimer(instance, 0, 20);
	}

	@SuppressWarnings("deprecation")
	public void start() {
		this.gameState = GameState.PLAYING;
		
		final Random random = new Random();
		final Team seekerTeam = scoreboard.getTeam(Teams.SEEKER.getName());

		if(seekerTeam != null) {
			if(!config.isSeekersNameTag()) {
				seekerTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
			} else {
				seekerTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			}
		}

		final Team hiderTeam = scoreboard.getTeam(Teams.HIDER.getName());
		
		final List<Roles> hiderRoles = Roles.getRolesByTeam(Teams.HIDER);
		final List<Roles> seekerRoles = Roles.getRolesByTeam(Teams.SEEKER);

		for(final Player player : data.getPlayers()) {
			player.setHealth(20); player.setFoodLevel(20);
			player.setLevel(0); player.setExp(0);
			player.getInventory().clear(); player.getInventory().setArmorContents(null);
			player.spigot().respawn();
			for(final PotionEffect potionEffect : player.getActivePotionEffects()) player.removePotionEffect(potionEffect.getType());
		}

		for(int i = 1; i <= config.getSeekersCount(); i++) {
			final List<Player> others = data.getPlayers().stream().filter(e -> !data.getSeekers().contains(e)).collect(Collectors.toList());
			final Player seeker = others.get(random.nextInt(others.size()));
			
			data.getSeekers().add(seeker);
			if(seekerTeam != null) seekerTeam.addEntry(seeker.getName());

			if(config.isRolesEnabled()) {
				final Roles role = seekerRoles.get(new Random().nextInt(seekerRoles.size()));
				if(role != null) {
					if(!config.isMultipleRoles()) seekerRoles.remove(role);
					data.getPlayersRoles().put(seeker, role);
				}
			}

			seeker.teleport(map.getCage());
		}

		final List<Player> hiders = data.getPlayers().stream().filter(e -> !data.getSeekers().contains(e)).collect(Collectors.toList());
		for(final Player hider : hiders) {
			data.getHiders().add(hider);
			if(hiderTeam != null) hiderTeam.addEntry(hider.getName());

			if(config.isRolesEnabled()) {
				final Roles role = hiderRoles.get(new Random().nextInt(hiderRoles.size()));
				if (role != null) {
					if (!config.isMultipleRoles()) hiderRoles.remove(role);
					data.getPlayersRoles().put(hider, role);

					hider.sendMessage(" \n§3Votre rôle §8: §6§l" + role.getDisplayName() + "\n §7" + role.getDescription() + "\n ");

					hider.getInventory().setHelmet(role.getHead());
					for (final PotionEffect potionEffect : role.getEffects()) {
						hider.addPotionEffect(potionEffect);
					}

					for (final ItemRole itemRole : role.getItems()) {
						hider.getInventory().setItem(itemRole.getSlot(), itemRole.getItemBuilder().toItemStack());
					}

					hider.sendTitle(ChatColor.GREEN + role.getDisplayName(), "§7Cachez-vous au plus vite !", 20, 5*20, 20);
				}
			} else {
				hider.sendTitle("", "§7Cachez-vous au plus vite !", 20, 5*20, 20);
			}

			hider.teleport(map.getBus());
		}

		for(final Player player : instance.getServer().getOnlinePlayers()) displayBoard(player);
		for(final World world : instance.getServer().getWorlds()) {
			world.setGameRuleValue("doDaylightCycle", "false");
			world.setTime(18000);
		}

		instance.getServer().broadcastMessage("§7L'équipe des " + Teams.SEEKER.getChatColor() + Teams.SEEKER.getDisplayName() + "s §7est composé de §8: " + Teams.SEEKER.getChatColor() + data.getSeekers().stream().map(HumanEntity::getName).collect(Collectors.joining(", ")) + " §7!");
		new Counting(getGame(), data.getSeekers(), false).runTaskTimer(instance, 0, 20);
	}
	
	@SuppressWarnings("deprecation")
	public void end(final Teams team, final List<Player> winners) {
		this.winner = team;
		this.gameState = GameState.ENDING;
		instance.getServer().getScheduler().cancelTasks(instance);

		for(final Player player : instance.getServer().getOnlinePlayers()) {
			player.getInventory().clear();
			player.updateInventory();
			player.teleport(Locations.END.getContent());
			player.setGameMode(GameMode.ADVENTURE);

			for(final PotionEffect potionEffect : player.getActivePotionEffects()) player.removePotionEffect(potionEffect.getType());
			this.displayBoard(player);
		}

		if(config.isRolesEnabled()) {
			instance.getServer().broadcastMessage(" \n§6Les §r" + team.getChatColor() + team.getDisplayName() + "s §6remportent la partie !\n§6Rôles jouées durant cette partie:\n" +
					(data.getPlayersRoles().entrySet().stream().map(e -> " §7• §r" + (scoreboard.getEntryTeam(e.getKey().getName()) != null ? scoreboard.getEntryTeam(e.getKey().getName()).getColor() : ChatColor.YELLOW) + e.getKey().getName() + " §8: §3" + e.getValue().getDisplayName()).collect(Collectors.joining("\n"))) + "\n ");
		}

		for(final Player winner : winners) {
			winner.sendTitle("§6Les §r" + team.getChatColor() + team.getDisplayName() + "s §6ont gagnés !", "§7Vous avez §agagné §7la partie.", 20, config.getEndingSeconds()*20, 20);
		}
		
		for(final Player looser : data.getPlayers().stream().filter(e -> !winners.contains(e)).collect(Collectors.toList())) {
			looser.sendTitle("§6Les §r" + team.getChatColor() + team.getDisplayName() + "s §6ont gagnés !", "§7Vous avez §cperdu §7la partie.", 20, config.getEndingSeconds()*20, 20);
		}
		
		for(final Player spectator : data.getSpectators()) {
			spectator.sendTitle("§6Les §r" + team.getChatColor() + team.getDisplayName() + "s §6ont gagnés !", "", 20, config.getEndingSeconds()*20, 20);
		}

		for(final World world : instance.getServer().getWorlds()) {
			world.setGameRuleValue("doDaylightCycle", "true");
			world.setTime(18000);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				for(final Player player : instance.getServer().getOnlinePlayers()) {
					player.kickPlayer("§cPartie terminée !\n \n§7Les §r" + team.getChatColor() + team.getDisplayName() + "s §8(" + team.getChatColor() + winners.stream().map(e -> e.getName()).collect(Collectors.joining(", ")) + "§8) §7remportent la partie !\n§7§oVous pouvez vous reconnectez dans quelques secondes afin de commencer une nouvelle partie.");
				}
				
				instance.getServer().reload();
			}
		}.runTaskLater(instance, config.getEndingSeconds()*20);
	}
	
	public JavaPlugin getInstance() {
		return instance;
	}
	public Game getGame() {
		return this;
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setWaitingState(WaitingState waitingState) {
		this.waitingState = waitingState;
	}
	
	public WaitingState getWaitingState() {
		return waitingState;
	}

	public void setMap(Maps map) {
		this.map = map;
	}
	public Maps getMap() {
		return map;
	}

	public Teams getWinner() {
		return winner;
	}

	public Data getData() {
		return data;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public enum GameState {
		WAITING,
		LAUNCHING,
		PLAYING,
		ENDING
	}
	
	public enum WaitingState {
		MAP_SELECT,
		TELEPORTING
	}
	
}
