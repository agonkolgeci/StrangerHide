package fr.jielos.strangerhide.listeners;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.game.Config;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.references.*;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import java.util.Map;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		final Game game = Main.getInstance().getGame();

		final ItemStack currentItem = event.getCurrentItem();
		final ItemStack cursor = event.getCursor();
		if(event.getWhoClicked() instanceof Player && currentItem != null) {
			final Player player = (Player) event.getWhoClicked();

			if(currentItem.getType().isAir()) return;

			if(event.getView().getTitle().equals(Inventories.MAP_SELECTOR.getTitle())) {
				event.setCancelled(true);
				
				final Maps votedMap = Maps.getMapBySlot(event.getRawSlot());
				if(votedMap != null) {
					final Maps lastVotedMap = game.getData().getPlayersMapsVotes().get(player.getUniqueId());
					if(lastVotedMap != null) {
						if(lastVotedMap != votedMap) {
							game.getData().getMapsVotes().put(lastVotedMap, game.getData().getMapsVotes().get(lastVotedMap)-1);
						} else return;
					}

					game.getData().getMapsVotes().put(votedMap, game.getData().getMapsVotes().get(votedMap)+1);
					game.getData().getPlayersMapsVotes().put(player.getUniqueId(), votedMap);

					for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
						entry.getValue().set("§7Votre vote: §a" + (game.getData().getPlayersMapsVotes().containsKey(entry.getKey().getUniqueId()) ? game.getData().getPlayersMapsVotes().get(entry.getKey().getUniqueId()).getShortName() : "§3Aucun"), 4);
					}

					if(votedMap != Maps.RANDOM) player.sendMessage("§7Vous avez voté pour la map §6" + votedMap.getName() + "§7.");
					else player.sendMessage("§7Vous avez voté pour une map sélectionnée §faléatoirement§7.");
				}

				for(final Player gamePlayer : game.getData().getPlayers()) {
					if(gamePlayer.getOpenInventory().getTitle().equals(Inventories.MAP_SELECTOR.getTitle())) {
						game.getData().getInventoriesGui().get(Inventories.MAP_SELECTOR).update(gamePlayer);
					}
				}
			} else if(event.getView().getTitle().equals(Inventories.CONFIGURATION.getTitle())) {
				event.setCancelled(true);
				if(!player.isOp()) {
					player.closeInventory();
					player.updateInventory();
					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
					player.sendMessage("§cVous n'avez pas les permissions suffisantes pour modifier les paramètres de la partie.");
					return;
				}

				final Configurations configuration = Configurations.getConfigurationBySlot(event.getRawSlot());
				if(configuration != null) {
					switch (configuration) {
						case MIN_PLAYERS: {
							int finalValue = game.getConfig().getMinPlayers();
							if(event.getClick() == ClickType.LEFT) finalValue = (game.getConfig().getMinPlayers()+1);
							if(event.getClick() == ClickType.MIDDLE) finalValue = (int) Config.DefaultConfig.MIN_PLAYERS.getValue();
							if(event.getClick() == ClickType.RIGHT) finalValue = (game.getConfig().getMinPlayers()-1);

							if(finalValue < 2) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de définir le nombre de joueurs minimum inférieur à 2.");
								return;
							}

							if(finalValue == game.getConfig().getMinPlayers()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
								return;
							}

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setMinPlayers(finalValue);

							if(game.getWaitingState() != Game.WaitingState.TELEPORTING) {
								if(game.getData().getPlayers().size() >= game.getConfig().getMinPlayers()) {
									game.teleport();
								}
							}

							break;
						}

						case MAX_PLAYERS: {
							int finalValue = game.getConfig().getMaxPlayers();
							if(event.getClick() == ClickType.LEFT) finalValue = (game.getConfig().getMaxPlayers()+1);
							if(event.getClick() == ClickType.MIDDLE) finalValue = (int) Config.DefaultConfig.MAX_PLAYERS.getValue();
							if(event.getClick() == ClickType.RIGHT) finalValue = (game.getConfig().getMaxPlayers()-1);

							if(finalValue < 2) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de définir le nombre de joueurs maximum inférieur à 2.");
								return;
							}

							if(finalValue < game.getData().getPlayers().size()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de définir le nombre de joueurs inférieur au nombre de joueurs connectés.");
								return;
							}

							if(game.getConfig().isRolesEnabled() && !game.getConfig().isMultipleRoles()) {
								if(finalValue > Roles.getRoles().size()) {
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
									player.sendMessage("§cImpossible de définir le nombre de joueurs supérieurs au nombre de rôles disponibles en ayant les rôles multiples désactivés.");
									return;
								}
							}

							if(finalValue == game.getConfig().getMaxPlayers()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
								return;
							}

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setMaxPlayers(finalValue);

							for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
								entry.getValue().set("§7Joueurs: §b" + game.getData().getPlayers().size() + "/" + game.getConfig().getMaxPlayers(), 5);
							}

							break;
						}

						case ROLES_ENABLED: {
							boolean finalValue = game.getConfig().isRolesEnabled();
							if(event.getClick() != ClickType.MIDDLE) finalValue = !game.getConfig().isRolesEnabled();
							else if(event.getClick() == ClickType.MIDDLE) finalValue = (boolean) Config.DefaultConfig.ROLES_ENABLED.getValue();

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setRolesEnabled(finalValue);

							if(!game.getConfig().isRolesEnabled()) {
								for (final Player gamePlayer : game.getData().getPlayers()) {
									if (gamePlayer.getOpenInventory().getTitle().equals(Inventories.ROLES_COMPOSITION.getTitle())) {
										gamePlayer.closeInventory();
										gamePlayer.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
										gamePlayer.sendMessage("§cLes rôles viennent d'être désactivés dans les paramètres de la partie.");
									}
								}
							} else if(game.getConfig().isRolesEnabled() && !game.getConfig().isMultipleRoles()) {
								if(game.getConfig().getMaxPlayers() > Roles.getRoles().size()) {
									game.getConfig().setMaxPlayers(Roles.getRoles().size());
									player.sendMessage("§cLe nombre de joueurs maximum vient d'être défini au nombre de rôles disponibles au total après la réactivation des rôles au vu des rôles multiples étant désactivés.");
									return;
								}

								if(game.getConfig().getSeekersCount() > Roles.getRolesByTeam(Teams.SEEKER).size()) {
									game.getConfig().setSeekersCount(Roles.getRolesByTeam(Teams.SEEKER).size());
									player.sendMessage("§cLe nombre de "+Teams.SEEKER.getDisplayName()+"s vient d'être défini au nombre de rôles de "+Teams.SEEKER.getDisplayName()+"s disponibles après la réactivation des rôles au vu des rôles multiples étant désactivés.");
								}

								if(!game.getConfig().isSpectatorMode()) {
									game.getConfig().setSpectatorMode(!game.getConfig().isSpectatorMode());
									player.sendMessage("§cLe mode spectateur pour les joueurs se faisant trouver vient d'être réactivé après la réactivation des rôles au vu des rôles multiples étant désactivés.");
								}
							}

							break;
						}

						case ROLES_COMPOSITION: {
							if(game.getConfig().isRolesEnabled()) {
								game.getData().getInventoriesGui().get(Inventories.ROLES_COMPOSITION).display(player);
							} else {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible d'accéder à la composition des rôles en ayant les rôles désactivés.");
							}

							break;
						}

						case SEEKERS_COUNT: {
							int finalValue = game.getConfig().getSeekersCount();
							if(event.getClick() == ClickType.LEFT) finalValue = (game.getConfig().getSeekersCount()+1);
							if(event.getClick() == ClickType.MIDDLE) finalValue = (int) Config.DefaultConfig.SEEKERS_COUNT.getValue();
							if(event.getClick() == ClickType.RIGHT) finalValue = (game.getConfig().getSeekersCount()-1);

							if(finalValue < 1) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de définir le nombre de "+Teams.SEEKER.getDisplayName()+"s inférieur à 1.");
								return;
							}

							if(event.getClick() != ClickType.MIDDLE && finalValue > (game.getData().getPlayers().size() - 1)) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de définir le nombre de "+Teams.SEEKER.getDisplayName()+"s supérieur à " + (game.getConfig().getSeekersCount()) + " au vu du nombre de joueurs connectés, il doit avoir au minimum 1 joueur dans chaque équipe.");
								return;
							}

							if(game.getConfig().isRolesEnabled() && !game.getConfig().isMultipleRoles()) {
								if(finalValue > Roles.getRolesByTeam(Teams.SEEKER).size()) {
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
									player.sendMessage("§cImpossible de définir le nombre de "+Teams.SEEKER.getDisplayName()+"s supérieur au nombre de rôles de "+Teams.SEEKER.getDisplayName()+" disponible en ayant les rôles multiples désactivés.");
									return;
								}
							}

							if(finalValue == game.getConfig().getSeekersCount()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
								return;
							}

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setSeekersCount(finalValue);

							break;
						}

						case SEEKERS_NAME_TAG: {
							boolean finalValue = game.getConfig().isSeekersNameTag();
							if(event.getClick() != ClickType.MIDDLE) finalValue = !game.getConfig().isSeekersNameTag();
							else if(event.getClick() == ClickType.MIDDLE) finalValue = (boolean) Config.DefaultConfig.SEEKERS_NAME_TAG.getValue();

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setSeekersNameTag(finalValue);

							final Team seekerTeam = game.getScoreboard().getTeam(Teams.SEEKER.getName());
							if(!game.getConfig().isSeekersNameTag()) {
								seekerTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
							} else {
								seekerTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
							}

							break;
						}

						case MULTIPLE_ROLES: {
							boolean finalValue = game.getConfig().isMultipleRoles();
							if(event.getClick() != ClickType.MIDDLE) finalValue = !game.getConfig().isMultipleRoles();
							else if(event.getClick() == ClickType.MIDDLE) finalValue = (boolean) Config.DefaultConfig.MULTIPLE_ROLES.getValue();

							if(finalValue == game.getConfig().isMultipleRoles()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
								return;
							}

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setMultipleRoles(finalValue);

							if(game.getConfig().isRolesEnabled() && !game.getConfig().isMultipleRoles()) {
								if(game.getConfig().getMaxPlayers() > Roles.getRoles().size()) {
									game.getConfig().setMaxPlayers(Roles.getRoles().size());
									player.sendMessage("§cLe nombre de joueurs maximum vient d'être défini au nombre de rôles disponibles au total après la désactivation des rôles multiples.");

									for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
										entry.getValue().set("§7Joueurs: §b" + game.getData().getPlayers().size() + "/" + game.getConfig().getMaxPlayers(), 5);
									}
								}

								if(game.getConfig().getSeekersCount() > Roles.getRolesByTeam(Teams.SEEKER).size()) {
									game.getConfig().setSeekersCount(Roles.getRolesByTeam(Teams.SEEKER).size());
									player.sendMessage("§cLe nombre de "+Teams.SEEKER.getDisplayName()+"s vient d'être défini au nombre de rôles de "+Teams.SEEKER.getDisplayName()+"s disponibles après la désactivation des rôles multiples.");
								}

								if(!game.getConfig().isSpectatorMode()) {
									game.getConfig().setSpectatorMode(!game.getConfig().isSpectatorMode());
									player.sendMessage("§cLe mode spectateur pour les joueurs se faisant trouver vient d'être réactivé après la désactivation de rôles multiples.");
								}
							}

							break;
						}

						case SPECTATOR_MODE: {
							boolean finalValue = game.getConfig().isSpectatorMode();
							if(event.getClick() != ClickType.MIDDLE) finalValue = !game.getConfig().isSpectatorMode();
							else if(event.getClick() == ClickType.MIDDLE) finalValue = (boolean) Config.DefaultConfig.SPECTATOR_MODE.getValue();

							if(game.getConfig().isRolesEnabled() && !game.getConfig().isMultipleRoles() && !finalValue) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de désactiver le mode spectateur après qu'un joueur se fait trouvé si les rôles multiples ne sont pas activés.");
								return;
							}

							if(finalValue == game.getConfig().isSpectatorMode()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
								return;
							}

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setSpectatorMode(finalValue);

							break;
						}

						case PVP: {
							boolean finalValue = game.getConfig().isPvp();
							if(event.getClick() != ClickType.MIDDLE) finalValue = !game.getConfig().isPvp();
							else if(event.getClick() == ClickType.MIDDLE) finalValue = (boolean) Config.DefaultConfig.PVP.getValue();

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setPvp(finalValue);

							break;
						}

						case CAMERAS_EQUIPMENT: {
							boolean finalValue = game.getConfig().isCamerasEquipment();
							if(event.getClick() != ClickType.MIDDLE) finalValue = !game.getConfig().isCamerasEquipment();
							else if(event.getClick() == ClickType.MIDDLE) finalValue = (boolean) Config.DefaultConfig.CAMERAS_EQUIPMENT.getValue();

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setCamerasEquipment(finalValue);

							break;
						}

						case COUNTING_TIME: {
							int finalValue = game.getConfig().getCountingSeconds();
							if(event.getClick() == ClickType.LEFT) finalValue = (game.getConfig().getCountingSeconds()+10);
							if(event.getClick() == ClickType.MIDDLE) finalValue = (int) Config.DefaultConfig.COUNTING_SECONDS.getValue();
							if(event.getClick() == ClickType.RIGHT) finalValue = (game.getConfig().getCountingSeconds()-10);

							if(finalValue < 10) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de définir le temps de comptage inférieur à 10 secondes.");
								return;
							}

							if(finalValue == game.getConfig().getCountingSeconds()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
								return;
							}

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setCountingSeconds(finalValue);

							break;
						}

						case SEARCHING_TIME: {
							int finalValue = game.getConfig().getSearchingSeconds();
							if(event.getClick() == ClickType.LEFT) finalValue = (game.getConfig().getSearchingSeconds()+60);
							if(event.getClick() == ClickType.MIDDLE) finalValue = (int) Config.DefaultConfig.SEARCHING_SECONDS.getValue();
							if(event.getClick() == ClickType.RIGHT) finalValue = (game.getConfig().getSearchingSeconds()-60);

							if(finalValue < 60) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de définir le temps de cherche inférieur à 1 minute.");
								return;
							}

							if(finalValue == game.getConfig().getSearchingSeconds()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
								return;
							}

							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
							game.getConfig().setSearchingSeconds(finalValue);

							break;
						}

						default: break;
					}

					if(configuration != Configurations.ROLES_COMPOSITION) {
						for(final Player gamePlayer : game.getData().getPlayers()) {
							if (gamePlayer.getOpenInventory().getTitle().equals(Inventories.CONFIGURATION.getTitle())) {
								game.getData().getInventoriesGui().get(Inventories.CONFIGURATION).update(gamePlayer);
							}
						}
					}
				}
			} else if(event.getView().getTitle().equals(Inventories.ROLES_COMPOSITION.getTitle())) {
				event.setCancelled(true);
				if(!player.isOp()) {
					player.closeInventory();
					player.updateInventory();
					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
					player.sendMessage("§cVous n'avez pas les permissions suffisantes pour modifier la composition de la partie.");
					return;
				}

				if(currentItem.getType() == Material.PLAYER_HEAD) {
					final Roles role = Roles.values()[event.getRawSlot()];

					boolean finalValue = !role.isEnabled();
					if(!finalValue) {
						if(Roles.getRoles().size()-1 < 2) {
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
							player.sendMessage("§cImpossible de désactivé ce rôle, il doit avoir minmum 2 rôles dans la partie.");
							return;
						}

						if(Roles.getRolesByTeam(role.getTeam()).size()-1 < 1) {
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
							player.sendMessage("§cImpossible de désactivé ce rôle, il doit avoir minmum 1 rôle par équipe.");
							return;
						}

						if(!game.getConfig().isMultipleRoles()) {
							if(Roles.getRoles().size() - 1 < game.getData().getPlayers().size()) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cImpossible de désactivé ce rôle en ayant les rôles multiples désactivés, il doit avoir au minimum 1 rôle pour chaque joueur.");
								return;
							}
						}
					}

					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
					role.setEnabled(finalValue);

					if(!game.getConfig().isMultipleRoles()) {
						game.getConfig().setMaxPlayers(game.getConfig().getMaxPlayers() + (finalValue ? +1 : -1));

						for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
							entry.getValue().set("§7Joueurs: §b" + game.getData().getPlayers().size() + "/" + game.getConfig().getMaxPlayers(), 5);
						}
					}

					for(final Player gamePlayer : game.getData().getPlayers()) {
						if (gamePlayer.getOpenInventory().getTitle().equals(Inventories.ROLES_COMPOSITION.getTitle())) {
							game.getData().getInventoriesGui().get(Inventories.ROLES_COMPOSITION).update(gamePlayer);
						}
					}
				} else {
					switch (currentItem.getType()) {
						case LIME_DYE: {
							if(Roles.getRoles().size() != Roles.values().length) {
								for(final Roles role : Roles.getDisabledRoles()) {
									role.setEnabled(true);
									if(!game.getConfig().isMultipleRoles()) {
										game.getConfig().setMaxPlayers(game.getConfig().getMaxPlayers()+1);
									}
								}

								player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);

								for(final Map.Entry<Player, BPlayerBoard> entry : Netherboard.instance().getBoards().entrySet()) {
									entry.getValue().set("§7Joueurs: §b" + game.getData().getPlayers().size() + "/" + game.getConfig().getMaxPlayers(), 5);
								}

								for(final Player gamePlayer : game.getData().getPlayers()) {
									if (gamePlayer.getOpenInventory().getTitle().equals(Inventories.ROLES_COMPOSITION.getTitle())) {
										game.getData().getInventoriesGui().get(Inventories.ROLES_COMPOSITION).update(gamePlayer);
									}
								}
							} else {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
								player.sendMessage("§cTous les rôles sont déjà activés.");
							}

							break;
						}

						case COMPARATOR: {
							game.getData().getInventoriesGui().get(Inventories.CONFIGURATION).display(player);
							break;
						}

						default: break;
					}
				}
			} else if(cursor != null && player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);

				if(currentItem.getType() == Material.SPECTRAL_ARROW || currentItem.getType() == Material.TIPPED_ARROW) return;
				if(cursor.getType() == Material.SPECTRAL_ARROW || cursor.getType() == Material.TIPPED_ARROW) return;
			}
		}
		
	}
}
