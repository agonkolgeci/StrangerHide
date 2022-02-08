package fr.jielos.strangerhide.references;

import fr.jielos.strangerhide.components.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.List;

public enum Configurations {

    MIN_PLAYERS(9*3-5, ConfigurationType.INTEGER, new ItemBuilder(Material.IRON_BARS).setName("§f§lJoueurs minimum").setLore("§7Permet de définir le nombre", "§7de joueurs minimum afin de", "§7commencer la partie automatiquement.")),
    MAX_PLAYERS(9*3-4, ConfigurationType.INTEGER, new ItemBuilder(Material.BARRIER).setName("§f§lJoueurs maximum").setLore("§7Permet de définir le nombre", "§7de joueurs au maximum qui", "§7peuvent rejoindre la partie.")),

    ROLES_ENABLED(9*3-8, ConfigurationType.BOOLEAN, new ItemBuilder(Material.TOTEM_OF_UNDYING).setName("§6§lRôles").setLore("§7Permet de définir si les rôles", "§7sont activés dans la partie.")),
    ROLES_COMPOSITION(9*3-7, ConfigurationType.MENU, new ItemBuilder(Material.LECTERN).setName("§e§lComposition des rôles").setLore("§7Permet de gérer la composition", "§7des rôles disponibles.")),

    SEEKERS_COUNT(0, ConfigurationType.INTEGER, new ItemBuilder(Material.DRAGON_HEAD).setName("§f§lNombre de " + Teams.SEEKER.getDisplayName() + "s").setLore("§7Permet de de définir le nombre", "§7de " + Teams.SEEKER.getDisplayName() + "s qui jouent.")),
    SEEKERS_NAME_TAG(1, ConfigurationType.BOOLEAN, new ItemBuilder(Material.NAME_TAG).setName("§f§lPseudo des " + Teams.SEEKER.getDisplayName() + "s").setLore("§7Permet de définir la visibilité", "§7des pseudonymes des " + Teams.SEEKER.getDisplayName() + "s.")),
    MULTIPLE_ROLES(2, ConfigurationType.BOOLEAN, new ItemBuilder(Material.ARMOR_STAND).setName("§f§lRôles multiples").setLore("§7Permet de définir si les", "§7joueurs peuvent avoir un", "§7rôle identique dans la partie.")),
    SPECTATOR_MODE(3, ConfigurationType.BOOLEAN, new ItemBuilder(Material.PHANTOM_MEMBRANE).setName("§f§lMode Spectateur").setLore("§7Permet de définir lorsque", "§7les joueurs se font trouvés", "§7deviennent des " + Teams.SEEKER.getDisplayName() + "s.")),
    PVP(4, ConfigurationType.BOOLEAN, new ItemBuilder(Material.DIAMOND_SWORD).hideAttributes().setName("§f§lPvP").setLore("§7Permet de définir si les dégâts", "§7pour les " + Teams.SEEKER.getDisplayName() + "s par l'équipe", "§7des" + Teams.HIDER.getDisplayName() + "s sont présents.")),
    CAMERAS_EQUIPMENT(5, ConfigurationType.BOOLEAN, new ItemBuilder(Material.LEATHER_CHESTPLATE).hideAttributes().setLeatherArmorColor(Color.BLACK).setName("§f§lÉquipement caméras").setLore("§7Permet de définir si " +Roles.BILLY_HARGROVE.getDisplayName(), "§7possède son équipement lorsqu'il", "§7visionne une caméra.")),

    COUNTING_TIME(9*3-2, ConfigurationType.INTEGER, new ItemBuilder(Material.LEGACY_EYE_OF_ENDER).setName("§f§lTemps de comptage").setLore("§7Permet de définir le temps de", "§7comptage des " + Teams.SEEKER.getDisplayName() + "s,", "§7augmente de §a10 §7en §a10§7.")),
    SEARCHING_TIME(9*3-1, ConfigurationType.INTEGER, new ItemBuilder(Material.CLOCK).setName("§f§lTemps de cherche").setLore("§7Permet de définir le temps de", "§7cherche de la partie (alias fin de partie),", "§7augmente de §aminute §7en §aminute§7."));

    final int slot;
    final ConfigurationType type;
    final ItemBuilder itemBuilder;
    Configurations(final int slot, final ConfigurationType type, final ItemBuilder itemBuilder) {
        this.slot = slot;
        this.type = type;
        this.itemBuilder = itemBuilder;
    }

    public int getSlot() {
        return slot;
    }

    public ConfigurationType getType() {
        return type;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

    public String getDisplayName() {
        return itemBuilder.toItemStack().getItemMeta().getDisplayName();
    }

    public List<String> getLore() {
        return itemBuilder.toItemStack().getItemMeta().getLore();
    }

    public static Configurations getConfigurationBySlot(final int slot) {
        for(Configurations configuration : values()) {
            if(configuration.getSlot() == slot) return configuration;
        }

        return null;
    }

    public enum ConfigurationType {
        INTEGER,
        BOOLEAN,
        MENU
    }
}
