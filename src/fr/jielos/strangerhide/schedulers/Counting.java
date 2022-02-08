package fr.jielos.strangerhide.schedulers;

import fr.jielos.strangerhide.components.ItemBuilder;
import fr.jielos.strangerhide.components.ItemRole;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.GameState;
import fr.jielos.strangerhide.references.Boards;
import fr.jielos.strangerhide.references.Items;
import fr.jielos.strangerhide.references.Roles;
import fr.jielos.strangerhide.references.Teams;
import fr.jielos.strangerhide.utils.Time;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class Counting extends BukkitRunnable {

	final Game game;

	List<Player> players;
	boolean newSeeker;
	int seconds;

	public Counting(final Game game, final List<Player> players, final boolean newSeeker) {
		this.game = game;

		this.players = players;
		this.newSeeker = newSeeker;
		this.seconds = game.getConfig().getCountingSeconds();
	}

	@Override
	public void run() {
		if(game.getGameState() == GameState.PLAYING) {
			if(seconds <= 0) {
				this.cancel();

				final Scoreboard scoreboard = game.getScoreboard();
				for(final Player seeker : players) {
					seeker.getInventory().setItem(Items.SEARCH_STICK.getSlot(), Items.SEARCH_STICK.getContent().toItemStack());
					seeker.getInventory().setHeldItemSlot(Items.SEARCH_STICK.getSlot());
					((CraftPlayer) seeker).getHandle().updateInventory(((CraftPlayer) seeker).getHandle().activeContainer);

					final Team team = scoreboard.getEntryTeam(seeker.getName());
					final Roles role = game.getData().getPlayersRoles().get(seeker);

					if(Netherboard.instance().getBoards().containsKey(seeker)) {
						final BPlayerBoard board = Netherboard.instance().createBoard(seeker, game.getScoreboard(), Boards.MAIN.getDisplayName());
						board.clear();

						board.set("§2", 10);
						board.set("§7En vie: §a" + game.getData().getHiders().size() + " joueur" + (game.getData().getHiders().size() > 1 ? "s" : ""), 9);

						if(game.getConfig().isRolesEnabled()) {
							if(role != null) {
								board.set("§8⋆ §7Rôle: " + (team != null ? team.getColor() : ChatColor.GRAY) + role.getDisplayName(), 8);
								board.set("§1", 7);
							}

							for(final ItemRole itemRole : role.getItems()) {
								seeker.getInventory().setItem(itemRole.getSlot(), itemRole.getItemBuilder().toItemStack());

								final int index = ArrayUtils.indexOf(role.getItems(), itemRole);
								board.set("§8- §7Capacité #" + (index + 1) + ": §aDisponible", (index + 3));
							}
						} else board.set("§3Partie classique", 6);

						board.set("§0", 2);
						board.set(Boards.MAIN.getFooter(), 1);
					}

					if(game.getConfig().isRolesEnabled()) {
						if(role != null) {
							seeker.sendMessage(" \n§3Votre rôle §8: §6§l" + role.getDisplayName() + "\n §7" + role.getDescription() + "\n ");
							seeker.sendTitle(ChatColor.RED + role.getDisplayName(), "§7C'est à vous de jouer !", 20, 5*20, 20);

							for(final PotionEffect potionEffect : role.getEffects()) {
								seeker.addPotionEffect(potionEffect);
							}
						}
					} else {
						seeker.sendTitle("", "§7C'est à vous de jouer !", 20, 5*20, 20);
					}

					seeker.getInventory().setArmorContents(new ItemStack[]{
							new ItemBuilder(new ItemStack(Material.LEATHER_BOOTS)).setName(Teams.SEEKER.getChatColor() + "Armure du " + Teams.SEEKER.getDisplayName()).setLeatherArmorColor(Color.BLACK).toItemStack(),
							new ItemBuilder(new ItemStack(Material.LEATHER_LEGGINGS)).setName(Teams.SEEKER.getChatColor() + "Armure du " + Teams.SEEKER.getDisplayName()).setLeatherArmorColor(Color.BLACK).toItemStack(),
							new ItemBuilder(new ItemStack(Material.LEATHER_CHESTPLATE)).setName(Teams.SEEKER.getChatColor() + "Armure du " + Teams.SEEKER.getDisplayName()).setLeatherArmorColor(Color.BLACK).toItemStack(),
							(game.getConfig().isRolesEnabled() && role != null ? role.getHead() : new ItemBuilder(new ItemStack(Material.LEATHER_HELMET)).setName(Teams.SEEKER.getChatColor() + "Armure du " + Teams.SEEKER.getDisplayName()).setLeatherArmorColor(Color.BLACK).toItemStack())
					});

					seeker.teleport(game.getMap().getBus());
				}

				if(!newSeeker) {
					game.getInstance().getServer().broadcastMessage("§c§lUn, deux, trois.... Prêt ou pas, j'arrive !");
					new Searching(game).runTaskTimer(game.getInstance(), 0, 20);
				}

				return;
			}

			if(!newSeeker) {
				for (final Player seeker : game.getInstance().getServer().getOnlinePlayers()) {
					seeker.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Le(s) "+Teams.SEEKER.getDisplayName()+"(s) arrive(ent) dans §e" + Time.format(seconds) + "§7."));
				}
			}
			
			for(final Player seeker : players) {
				seeker.sendTitle("", "§7Téléportation dans §e" + Time.format(seconds) + "§7.", 0, 2*20, 0);
			}
			
			seconds--;
		}
	}
	
}
