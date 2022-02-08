package fr.jielos.strangerhide.schedulers;

import fr.jielos.strangerhide.components.ItemBuilder;
import fr.jielos.strangerhide.components.ItemRole;
import fr.jielos.strangerhide.game.Game;
import fr.jielos.strangerhide.references.Roles;
import fr.jielos.strangerhide.utils.Time;
import fr.minuskube.netherboard.Netherboard;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SkillUsage extends BukkitRunnable {

    final Game game;
    final Player player;
    final Roles role;
    final ItemRole item;
    public SkillUsage(final Game game, final Player player, final Roles role, final ItemRole item) {
        this.game = game;
        this.player = player;
        this.role = role;
        this.item = item;

        player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1F, 0.5F);
        player.sendMessage("§7Vous avez activé l'une de vos capacités §8: §r" + item.getName() + "§7.");

        if(item.getUses() != Integer.MAX_VALUE) {
            if(item.getUses() >= 1) item.removeUses(1);

            if(item.getUses() <= 0) {
                player.getInventory().remove(item.getItemBuilder().toItemStack());
                return;
            }
        }

        player.getInventory().setItem(item.getSlot(), new ItemBuilder(new ItemStack(Material.BARRIER)).setName(item.getName() + " - Rechargement ...").toItemStack());

        this.seconds = item.getDelay();
        this.runTaskTimer(game.getInstance(), 0, 20);
    }

    int seconds = 0;

    @Override
    public void run() {
        final int index = ArrayUtils.indexOf(role.getItems(), item);

        if(seconds <= 0) {
            this.cancel();

            if(player.isOnline() && game.getGameState() == Game.GameState.PLAYING && !game.getData().getSpectators().contains(player) && game.getData().getPlayersRoles().get(player) == role) {
                if(item.getUses() != Integer.MAX_VALUE) {
                    item.getItemBuilder().setName(item.getName() + " §8(§7" + item.getUses() + " utilisations§8)");
                }

                player.getInventory().setItem(item.getSlot(), item.getItemBuilder().toItemStack());
                player.getInventory().setHelmet(role.getHead());
                player.updateInventory();

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);

                if(Netherboard.instance().getBoards().containsKey(player)) {
                    Netherboard.instance().getBoard(player).set("§8- §7Capacité #" + (index+1) + ": §aDisponible", (index+3));
                }
            }

            return;
        }

        if(Netherboard.instance().getBoards().containsKey(player)) {
            Netherboard.instance().getBoard(player).set("§8- §7Capacité #" + (index+1) + ": §e" + Time.format(seconds), (index+3));
        }
        seconds--;
    }
}