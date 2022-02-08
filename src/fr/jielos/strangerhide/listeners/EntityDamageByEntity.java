package fr.jielos.strangerhide.listeners;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.GameState;
import fr.jielos.strangerhide.references.Items;
import fr.jielos.strangerhide.references.Roles;
import fr.jielos.strangerhide.references.Teams;
import fr.jielos.strangerhide.schedulers.Counting;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.*;

public class EntityDamageByEntity implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
		final Game game = Main.getInstance().getGame();

		if(event.getEntity().getType() == EntityType.ITEM_FRAME) {
			if(event.getEntity() instanceof Player) {
				final Player player = (Player) event.getEntity();
				if(player.getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
			} else {
				event.setCancelled(true);
			}
		}
		
		if(event.getEntity() instanceof Player) {
			final Player player = (Player) event.getEntity();
			if(event.getDamager() instanceof Player) {
				final Player damager = (Player) event.getDamager();
				event.setDamage(0F);

				if(game.getGameState() == GameState.PLAYING) {
					if(game.getData().getHiders().contains(player) && game.getData().getSeekers().contains(damager)) {
						if(!damager.getItemInHand().getType().isAir() && damager.getItemInHand().getItemMeta().getDisplayName().equals(Items.SEARCH_STICK.getContent().toItemStack().getItemMeta().getDisplayName())) {
							game.getData().getHiders().remove(player);
							game.getInstance().getServer().broadcastMessage(Teams.HIDER.getChatColor() + player.getName() + " §6vient d'être trouvée par les " + Teams.SEEKER.getChatColor() + Teams.SEEKER.getDisplayName() + "s §6!");

							player.getInventory().clear();
							player.getInventory().setArmorContents(null);
							((CraftPlayer) player).getHandle().updateInventory(((CraftPlayer) player).getHandle().activeContainer);

							for(final PotionEffect potionEffect : player.getActivePotionEffects()) {
								player.removePotionEffect(potionEffect.getType());
							}

							player.sendTitle("§cVous avez été trouvé(e) !", "", 20, 5*20, 20);
							player.setVelocity(new Vector(0, 0, 0));

							if(game.getData().getHiders().size() <= 0) {
								game.end(Teams.SEEKER, game.getData().getSeekers());
								return;
							} else if(game.getData().getSeekers().size() <= 0) {
								game.end(Teams.HIDER, game.getData().getHiders());
								return;
							}

							final Scoreboard scoreboard = game.getScoreboard();
							final Team playerTeam = scoreboard.getEntryTeam(player.getName());
							if(playerTeam != null) playerTeam.removeEntry(player.getName());

							if(game.getConfig().isSpectatorMode() || !game.getConfig().isMultipleRoles()) {
								game.getData().getPlayers().remove(player);
								game.getData().getSpectators().add(player);

								player.setGameMode(GameMode.SPECTATOR);
								player.teleport(damager.getLocation());
								player.setVelocity(new Vector(0.2, 0.4, 0.2));
							} else if(!game.getConfig().isSpectatorMode() && game.getConfig().isMultipleRoles()) {
								final Team seekerTeam = scoreboard.getTeam(Teams.SEEKER.getName());
								final List<Roles> seekerRoles = Roles.getRolesByTeam(Teams.SEEKER);

								game.getData().getSeekers().add(player);
								if(seekerTeam != null) seekerTeam.addEntry(player.getName());

								final Roles role = seekerRoles.get(new Random().nextInt(seekerRoles.size()));
								if(role != null) game.getData().getPlayersRoles().put(player, role);

								player.teleport(game.getMap().getCage());
								game.displayBoard(player);

								new Counting(game, Collections.singletonList(player), true).runTaskTimer(game.getInstance(), 0, 20);
							}

							for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
								entry.getValue().set("§7En vie: §a" + game.getData().getHiders().size() + " joueur" + (game.getData().getHiders().size() > 1 ? "s" : ""), 9);
							}
						} else damager.sendTitle("", "§cVous devez frapper avec le bâton du " + Teams.SEEKER.getDisplayName() + " !", 0, 20, 0);

						event.setCancelled(true);
					} else if(game.getData().getHiders().contains(damager) && game.getData().getSeekers().contains(player)) {
						if(!game.getConfig().isPvp() && game.getData().getPlayersRoles().get(damager) != Roles.STEVE_HARRINGTON) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
}
