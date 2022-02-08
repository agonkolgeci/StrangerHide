package fr.jielos.strangerhide.components;

import java.util.List;

public class ItemRole {

    final ItemBuilder itemBuilder;
    final int slot;
    final int delay;

    String name;
    int uses;

    public ItemRole(final ItemBuilder itemBuilder, final int slot, final int delay, final int uses) {
        if(delay > 0) {
            List<String> lore = itemBuilder.toItemStack().getItemMeta().getLore();
            if(lore != null) {
                lore.add(""); lore.add("§6Délai §8: §e" + delay + " secondes");
                itemBuilder.setLore(lore.toArray(new String[0]));
            }
        }

        this.name = itemBuilder.toItemStack().getItemMeta().getDisplayName();
        if(uses != Integer.MAX_VALUE) itemBuilder.setName(name + " §8(§7" + uses + " utilisation"+(uses > 1 ? "s" : "")+"§8)");

        this.itemBuilder = itemBuilder;
        this.slot = slot;
        this.delay = delay;
        this.uses = uses;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public int getDelay() {
        return delay;
    }

    public int getUses() {
        return uses;
    }

    public void removeUses(int value) {
        this.uses -= value;
    }
}
