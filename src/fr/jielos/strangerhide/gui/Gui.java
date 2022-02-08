package fr.jielos.strangerhide.gui;

import fr.jielos.strangerhide.components.ItemBuilder;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.references.*;
import fr.jielos.strangerhide.utils.Time;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Gui {

    final Game game;
    final Inventories inventoryData;
    public Gui(final Game game, final Inventories inventoryData) {
        this.game = game;
        this.inventoryData = inventoryData;
    }

    public void display(final Player player) {
        final Inventory inventory = game.getInstance().getServer().createInventory(player, inventoryData.getSize(), inventoryData.getTitle());

        player.closeInventory();
        player.openInventory(inventory);
        this.update(player);
    }

    public void update(final Player player) {
        switch (inventoryData) {
            case MAP_SELECTOR: {
                for(final Maps map : Maps.values()) {
                    final List<String> lore = Arrays.asList(map.getSynopsis());
                    lore.replaceAll(e -> "§7§o" + e);

                    if(map != Maps.RANDOM) player.getOpenInventory().setItem(map.getSlot(), new ItemBuilder(new ItemStack((game.getData().getPlayersMapsVotes().get(player.getUniqueId()) == map ? Material.LEGACY_MAP : Material.LEGACY_EMPTY_MAP))).setName("§6§l" + map.getName() + " §e("+game.getData().getMapsVotes().get(map)+" votes) " + (game.getData().getPlayersMapsVotes().get(player.getUniqueId()) == map ? "§2§l✔" : "")).setLore((String[]) lore.toArray()).hideAttributes().toItemStack());
                    else player.getOpenInventory().setItem(map.getSlot(), new ItemBuilder(new ItemStack(Material.BARRIER)).setName("§f§l" + map.getName() + " §7("+game.getData().getMapsVotes().get(map)+" votes) " + (game.getData().getPlayersMapsVotes().get(player.getUniqueId()) == map ? "§2§l✔" : "")).setLore((String[]) lore.toArray()).toItemStack());
                }

                break;
            }

            case CONFIGURATION: {
                for(final Configurations configuration : Configurations.values()) {
                    String value = null;

                    switch (configuration) {
                        case MIN_PLAYERS: {
                            value = (ChatColor.GREEN + String.valueOf(game.getConfig().getMinPlayers()));
                            break;
                        }

                        case MAX_PLAYERS: {
                            value = (ChatColor.RED + String.valueOf(game.getConfig().getMaxPlayers()));
                            break;
                        }

                        case ROLES_ENABLED: {
                            value = (game.getConfig().isRolesEnabled() ? ChatColor.GREEN + "Activé" : ChatColor.RED + "Désactivé");
                            break;
                        }

                        case SEEKERS_COUNT: {
                            value = (ChatColor.DARK_RED + String.valueOf(game.getConfig().getSeekersCount()));
                            break;
                        }

                        case SEEKERS_NAME_TAG: {
                            value = (game.getConfig().isSeekersNameTag() ? ChatColor.GREEN + "Activé" : ChatColor.RED + "Désactivé");
                            break;
                        }

                        case MULTIPLE_ROLES: {
                            value = (game.getConfig().isMultipleRoles() ? ChatColor.GREEN + "Activé" : ChatColor.RED + "Désactivé");
                            break;
                        }

                        case SPECTATOR_MODE: {
                            value = (game.getConfig().isSpectatorMode() ? ChatColor.GREEN + "Activé" : ChatColor.RED + "Désactivé");
                            break;
                        }

                        case PVP: {
                            value = (game.getConfig().isPvp() ? ChatColor.GREEN + "Activé" : ChatColor.RED + "Désactivé");
                            break;
                        }

                        case CAMERAS_EQUIPMENT: {
                            value = (game.getConfig().isCamerasEquipment() ? ChatColor.GREEN + "Activé" : ChatColor.RED + "Désactivé");
                            break;
                        }

                        case COUNTING_TIME: {
                            value = (ChatColor.YELLOW + Time.format(game.getConfig().getCountingSeconds()));
                            break;
                        }

                        case SEARCHING_TIME: {
                            value = (ChatColor.YELLOW + Time.format(game.getConfig().getSearchingSeconds()));
                            break;
                        }

                        default: break;
                    }

                    final List<String> lore = configuration.getLore();

                    lore.add(" ");

                    lore.add((configuration.getType() == Configurations.ConfigurationType.INTEGER ? "§eClic-gauche §8- §aAugmenter" :
                                    (configuration.getType() == Configurations.ConfigurationType.BOOLEAN ? "§eClic §8- §2Inverser la propriété" :
                                            (configuration.getType() == Configurations.ConfigurationType.MENU ? "§eClic §8- §6Ouvrir le menu" :
                                                    ("")))));

                    if(configuration.getType() != Configurations.ConfigurationType.MENU) {
                        lore.add("§eClic-molette §8- §r" + ChatColor.GOLD + "Par défaut");
                    }

                    if(configuration.getType() == Configurations.ConfigurationType.INTEGER) {
                        lore.add("§eClic-droit §8- §cRéduire");
                    }


                    final ItemBuilder itemBuilder = new ItemBuilder(new ItemStack(configuration.getItemBuilder().toItemStack().getType())).setName(configuration.getDisplayName() + (value != null ? (": " + value) : "")).setLore(lore.toArray(new String[0]));

                    if(!game.getConfig().isRolesEnabled()) {
                        if(configuration == Configurations.MULTIPLE_ROLES || configuration == Configurations.CAMERAS_EQUIPMENT) {
                            player.getOpenInventory().setItem(configuration.getSlot(), null);
                        } else {
                            player.getOpenInventory().setItem(configuration.getSlot(), itemBuilder.toItemStack());
                        }
                    } else {
                        player.getOpenInventory().setItem(configuration.getSlot(), itemBuilder.toItemStack());
                    }
                }

                break;
            }

            case ROLES_COMPOSITION: {
                for(final Roles role : Roles.values()) {
                    final int index = ArrayUtils.indexOf(Roles.values(), role);

                    final ItemBuilder itemBuilder = new ItemBuilder(new ItemStack(role.getHead())).setName("§f§l" + role.getDisplayName()).setLore("§7Équipe: §r" + role.getTeam().getChatColor() + role.getTeam().getDisplayName(), "§7État: §r" + (role.isEnabled() ? "§a§lActivé" : "§c§lDésactivé"));
                    player.getOpenInventory().setItem(index, itemBuilder.toItemStack());
                }

                player.getOpenInventory().setItem(player.getOpenInventory().getTopInventory().getSize()-2, new ItemBuilder(Material.LIME_DYE).setName("§a§lActiver tout").setLore("§eClic §8- §aActiver tout").toItemStack());
                player.getOpenInventory().setItem(player.getOpenInventory().getTopInventory().getSize()-1, new ItemBuilder(Material.COMPARATOR).setName("§c§lRevenir à la configuration").setLore("§eClic §8- §6Retour").toItemStack());

                break;
            }

            default: break;
        }
    }

}
