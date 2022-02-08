package fr.jielos.strangerhide.references;

import fr.jielos.strangerhide.components.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

	MAP_SELECTOR(0, new ItemBuilder(new ItemStack(Material.LEGACY_EMPTY_MAP)).setName("§3§lSélecteur de map §r§7(Clic-droit)")),
	JUMP_TELEPORT(1, new ItemBuilder(new ItemStack(Material.FEATHER)).setName("§f§lTéléportation au jump §r§7(Clic-droit)")),
	CONFIGURATION(3, new ItemBuilder(new ItemStack(Material.COMPARATOR)).setName("§c§lConfiguration de la partie §r§7(Clic-droit)")),
	
	SEARCH_STICK(0, new ItemBuilder(new ItemStack(Material.STICK)).setName("§c§lBâton du " + Teams.SEEKER.getDisplayName()));

	final int slot;
	final ItemBuilder content;
	Items(final int slot, final ItemBuilder content) {
		this.slot = slot;
		this.content = content;
	}

	public int getSlot() {
		return slot;
	}
	public ItemBuilder getContent() {
		return content;
	}
	public String getDisplayName() {
		return content.toItemStack().getItemMeta().getDisplayName();
	}

}