package fr.jielos.strangerhide.components;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;

public class ItemBuilder {
    private ItemStack is;
 
    public ItemBuilder(Material m) {
        this(m, 1);
    }
 
    public ItemBuilder(ItemStack is) {
        this.is = is;
    }
 
    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }
 
    @SuppressWarnings("deprecation")
	public ItemBuilder(Material m, int amount, short meta){
        is = new ItemStack(m, amount, meta);
    }
 
    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }
 
    @SuppressWarnings("deprecation")
	public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }
 
    public ItemBuilder setName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }
 
    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }
 
	@SuppressWarnings("deprecation")
	public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }
 
    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setPotionColor(Color color) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.setColor(color);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setBasePotionData(PotionData data) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.setBasePotionData(data);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addPotionCustomEffect(PotionEffect effect) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.addCustomEffect(effect, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta im = is.getItemMeta();
        im.setUnbreakable(unbreakable);
        return this;
    }

    @SuppressWarnings("deprecation")
	public ItemBuilder setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }
 
    public ItemBuilder setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder hideAttributes() {
    	ItemMeta im = is.getItemMeta();
    	im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	is.setItemMeta(im);
    	return this;
    }
 
    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }
 
    public ItemStack toItemStack() {
        return is;
    }
}