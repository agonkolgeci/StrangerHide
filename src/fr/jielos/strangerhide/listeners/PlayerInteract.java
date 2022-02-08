package fr.jielos.strangerhide.listeners;

import fr.jielos.strangerhide.Main;
import fr.jielos.strangerhide.components.Cuboid;
import fr.jielos.strangerhide.components.ItemBuilder;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.game.Game.GameState;
import fr.jielos.strangerhide.game.Game.WaitingState;
import fr.jielos.strangerhide.references.*;
import fr.jielos.strangerhide.schedulers.SkillUsage;
import fr.minuskube.netherboard.Netherboard;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.FlowerPot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

@SuppressWarnings("deprecation")
public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event) {
		final Game game = Main.getInstance().getGame();
		final Player player = event.getPlayer();

		if(event.getClickedBlock() != null && event.getAction() == Action.PHYSICAL) {
			if(event.getClickedBlock().getType() == Material.FARMLAND) event.setCancelled(true);
			if(event.getClickedBlock() instanceof FlowerPot && player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			}
		}
		
		if(game.getGameState() != GameState.PLAYING && player.getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
		if(event.getItem() != null) {
			final String currentItemName = event.getItem().getItemMeta().getDisplayName();

			if(game.getWaitingState() == WaitingState.MAP_SELECT || game.getWaitingState() == WaitingState.TELEPORTING) {
				if(currentItemName.equals(Items.MAP_SELECTOR.getDisplayName())) {
					game.getData().getInventoriesGui().get(Inventories.MAP_SELECTOR).display(player);
					event.setCancelled(true);
				} else if(currentItemName.equals(Items.JUMP_TELEPORT.getDisplayName())) {
					if(player.getLocation().distance(Locations.JUMP.getContent()) >= 2) {
						player.teleport(Locations.JUMP.getContent());
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
					} else if(player.getLocation().distance(Locations.JUMP.getContent()) < 2) {
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cVous êtes proche du jump !"));
					}
				} else if(currentItemName.equals(Items.CONFIGURATION.getDisplayName())) {
					if(!player.isOp()) {
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
						player.sendMessage("§cVous n'avez pas les permissions suffisantes pour modifier les paramètres de la partie.");
						return;
					}

					game.getData().getInventoriesGui().get(Inventories.CONFIGURATION).display(player);
					event.setCancelled(true);
				}
			}

			if(game.getGameState() == GameState.PLAYING && game.getConfig().isRolesEnabled()) {
				final Roles role = game.getData().getPlayersRoles().get(player);
				if(role != null) {
					if(event.getItem().getType() == Material.BARRIER) return;

					final int slot = player.getInventory().getHeldItemSlot();

					boolean used = false;
					int skill = Integer.MAX_VALUE;

					if(role == Roles.BILLY_HARGROVE) {
						int x = -1;
						if(slot == role.getItems()[0].getSlot()) x = 0;
						if(slot == role.getItems()[1].getSlot()) x = 1;
						if(slot == role.getItems()[2].getSlot()) x = 2;

						if(x >= 0) {
							if(game.getData().getPlayersCameras().containsKey(player)) {
								player.sendMessage("§c§oVous êtes déjà en train de visionner une caméra.");
								return;
							}

							final Location lastLocation = player.getLocation();
							game.getData().getPlayersCameras().put(player, game.getMap().getCameras()[x]);

							game.getMap().getCameras()[x].getBlock().getRelative(BlockFace.DOWN).setType(Material.BARRIER);
							player.teleport(game.getMap().getCameras()[x]);
							player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));

							player.getInventory().setItem(role.getItems()[x].getSlot(), new ItemBuilder(new ItemStack(Material.BARRIER)).setName("§4Visionnée").toItemStack());
							if(!game.getConfig().isCamerasEquipment()) player.getInventory().setArmorContents(null);
							((CraftPlayer) player).getHandle().updateInventory(((CraftPlayer) player).getHandle().activeContainer);

							new BukkitRunnable() {
								@Override
								public void run() {
									if(game.getData().getPlayersCameras().containsKey(player)) {
										if(!game.getConfig().isCamerasEquipment()) {
											player.getInventory().setArmorContents(new ItemStack[]{
													new ItemBuilder(new ItemStack(Material.LEATHER_BOOTS)).setName(Teams.SEEKER.getChatColor() + "Armure du " + Teams.SEEKER.getDisplayName()).setLeatherArmorColor(Color.BLACK).toItemStack(),
													new ItemBuilder(new ItemStack(Material.LEATHER_LEGGINGS)).setName(Teams.SEEKER.getChatColor() + "Armure du " + Teams.SEEKER.getDisplayName()).setLeatherArmorColor(Color.BLACK).toItemStack(),
													new ItemBuilder(new ItemStack(Material.LEATHER_CHESTPLATE)).setName(Teams.SEEKER.getChatColor() + "Armure du " + Teams.SEEKER.getDisplayName()).setLeatherArmorColor(Color.BLACK).toItemStack(),
													role.getHead()
											});
										}

										player.getInventory().setHelmet(role.getHead());
										((CraftPlayer) player).getHandle().updateInventory(((CraftPlayer) player).getHandle().activeContainer);

										player.teleport(lastLocation);
										player.removePotionEffect(PotionEffectType.INVISIBILITY);

										game.getData().getPlayersCameras().remove(player);
									}
								}
							}.runTaskLater(game.getInstance(), 10 * 20);

							skill = x;
							used = true;
						}
					} else if(role == Roles.ROBIN_BUCKLEY) {
						int x = -1;
						if(slot == role.getItems()[0].getSlot()) x = 0;
						if(slot == role.getItems()[1].getSlot()) x = 1;

						skill = x;
						used = true;
					} else if(role == Roles.JIM_HOPPER) {
						int x = -1;
						if(slot == role.getItems()[0].getSlot()) x = 0;

						skill = x;
						used = true;
					}

					if(slot == role.getItems()[0].getSlot()) {
						skill = 0;

						switch (role) {
							case MIKE_WHEELER: {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5*20, 2, false, false));

								used = true;
								break;
							}

							case WILL_BYERS: {
								player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 8*20, 0, false, false));
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 8*20, 1, false, false));

								player.getInventory().setHelmet(new ItemStack(Material.AIR));
								((CraftPlayer) player).getHandle().updateInventory(((CraftPlayer) player).getHandle().activeContainer);

								new BukkitRunnable() {
									@Override
									public void run() {
										if(game.getData().getHiders().contains(player)) {
											player.getInventory().setHelmet(role.getHead());
										}

										player.removePotionEffect(PotionEffectType.INVISIBILITY);
									}
								}.runTaskLater(game.getInstance(), 8*20);

								used = true;
								break;
							}

							case DUSTIN_HENDERSON: {
								for(final Player seeker : game.getData().getSeekers()) {
									seeker.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5*20, 0, false, false));
								}

								used = true;
								break;
							}

							case LUCAS_SINCLAIR: {
								for(final Player seeker : game.getData().getSeekers()) {
									seeker.setGlowing(true);
								}

								new BukkitRunnable() {
									@Override
									public void run() {
										for(final Player seeker : game.getData().getSeekers()) {
											seeker.setGlowing(false);
										}
									}
								}.runTaskLater(game.getInstance(), 15*20);

								used = true;
								break;
							}

							case ELEVEN: {
								if(event.getClickedBlock() != null) {
									final Location location = event.getClickedBlock().getLocation().add(0, 1, 0);

									Cuboid cuboid = null;
									switch (player.getFacing()) {
										case SOUTH: {
											cuboid = new Cuboid(location.clone().add(1, 2, 0), location.clone().subtract(1, 0, 0));
											break;
										}

										case WEST: {
											cuboid = new Cuboid(location.clone().add(0, 2, 1), location.clone().subtract(0, 0, 1));
											break;
										}

										case NORTH: {
											cuboid = new Cuboid(location.clone().subtract(1, 0, 0), location.clone().add(1, 2, 0));
											break;
										}

										case EAST: {
											cuboid = new Cuboid(location.clone().subtract(0, 0, 1), location.clone().add(0, 2, 1));
											break;
										}

										default: break;
									}

									if(cuboid != null) {
										final List<Block> blocks = cuboid.getBlocks();

										for(final Block block : blocks) {
											if(block.getType().isAir() && block.getRelative(BlockFace.DOWN).getType() != Material.FARMLAND) {
												block.setType(Material.COBWEB);
											} else {
												blocks.remove(block);
											}
										}

										new BukkitRunnable() {
											@Override
											public void run() {
												for(final Block block : blocks) {
													block.setType(Material.AIR);
												}
											}
										}.runTaskLater(game.getInstance(), 15*20);

										used = true;
									}

									break;
								}
							}

							case MAXINE_HARGROVE: {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 8*20, 1, false, false));

								used = true;
								break;
							}

							case JONATHAN_BYERS: {
								event.setCancelled(true);

								player.setVelocity(new Vector(0, 1.6, 0));
								player.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
								((CraftPlayer) player).getHandle().updateInventory(((CraftPlayer) player).getHandle().activeContainer);

								new BukkitRunnable() {
									@Override
									public void run() {
										player.setGliding(true);

										new BukkitRunnable() {
											@Override
											public void run() {
												if(player.isOnGround()) {
													player.setGliding(false);
													player.getInventory().setChestplate(new ItemStack(Material.AIR));
													cancel();
												}
											}
										}.runTaskTimer(game.getInstance(), 0, 20);
									}
								}.runTaskLater(game.getInstance(), 16);

								used = true;
								break;
							}

							case DEMOGORGON: {
								for(final Player hider : game.getData().getHiders()) {
									hider.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 16*20, 0, false, false));
									hider.sendTitle("§c⚠", "", 0, 15*20, 0);
									hider.sendMessage("§7Vous avez reçu un effet de §cnausée §7& de §cconfusion §7par le §c" + role.getDisplayName() + "§7.");
								}

								new BukkitRunnable() {
									int seconds = 15;

									@Override
									public void run() {
										if(seconds <= 0) {
											cancel();
											return;
										}

										for(final Player hider : game.getData().getHiders()) {
											hider.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false, false));
										}

										seconds--;
									}
								}.runTaskTimer(game.getInstance(), 0, 20);

								player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10*20, 1, false, false));

								used = true;
								break;
							}

							case MIND_FLAYER: {
								for(final Player hider : game.getData().getHiders()) {
									hider.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 6*20, 1, false, false));
									hider.sendTitle("§c⚠", "", 0, 6*20, 0);
									hider.sendMessage("§7Vous avez reçu un effet de §clenteur 2 §7par §c" + role.getDisplayName() + "§7.");
								}

								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 1, false, false));

								used = true;
								break;
							}

							default: break;
						}
					}

					if(used && skill != Integer.MAX_VALUE) {
						final int index = ArrayUtils.indexOf(role.getItems(), role.getItems()[skill]);
						if(Netherboard.instance().getBoards().containsKey(player)) {
							Netherboard.instance().getBoard(player).set("§8- §7Capacité #" + (index+1) + ": §cUtilisée", (index+3));
						}

						if(role.getItems()[skill].getDelay() > 0 || role.getItems()[skill].getUses() != Integer.MAX_VALUE) {
							new SkillUsage(game, player, role, role.getItems()[skill]);
						}
					}
				}
			}
		}
	}

}
