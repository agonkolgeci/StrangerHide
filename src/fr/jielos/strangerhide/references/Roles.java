package fr.jielos.strangerhide.references;

import fr.jielos.strangerhide.components.ItemBuilder;
import fr.jielos.strangerhide.components.ItemRole;
import fr.jielos.strangerhide.utils.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Roles {

    MIKE_WHEELER("Mike Wheeler", "Né le 7 avril 1971, Mike est membre de la Famille Wheeler, vivant à Hawkins, Indiana avec sa mère Karen, son père Ted et ses deux sœurs Nancy et Holly. Nancy soupçonnait que leurs parents ne s'aimaient jamais vraiment et qu'ils se mariaient uniquement pour avoir une «famille nucléaire parfaite».", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFhN2QwNWZlNmU2MThhMzlhYTlmNWFjZWIxMWI3YWUzNzQxNjAwYWVlM2I2NjczNzQ3Y2E1ZGU3NTMxZjZhNSJ9fX0=", Teams.HIDER, new PotionEffect[]{new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0, false, false)}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.FEATHER)).setName("§aCapacité #1").setLore("§7Vous applique un effet de", "§bvitesse III §7pendant 5 secondes."), 1, 30, Integer.MAX_VALUE)}),
    WILL_BYERS("Will Byers", "Will est le fils de Lonnie et Joyce Byers et le frère cadet de Jonathan Byers. Il est le meilleur ami de Mike Wheeler, Lucas Sinclair, Dustin Henderson, Onze et Max Mayfield. Dans la nuit du 6 novembre 1983, Will a été enlevé par le Démogorgon et emmené dans une dimension alternative surnommée le Monde à l'envers.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWM1OWI0NjI4MTI3MjMwMWZlOGVlYjdiY2ExMzdlMTdiNjBkMmVhZDhkYjJhM2RiOWY0NmY5ZTY3YWI2YjcxYSJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.PHANTOM_MEMBRANE)).setName("§aCapacité #1").setLore("§7Vous applique un effet d'§3invisibilité", "§7avec un effet de §clenteur 2", "§7pendant 8 secondes."), 1, 10, 2)}),
    DUSTIN_HENDERSON("Dustin Henderson", "Dustin Henderson, interprété par Gaten Matarazzo, est un protagoniste majeur des trois premières saisons de Stranger Things. Il est le meilleur ami de Mike Wheeler, Lucas Sinclair, Onze, Will Byers et Max Mayfield. ... Cependant, il danse avec la sœur aînée de Mike, Nancy, disant qu'il gagnera un jour le cœur d'une fille.", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNhOGY3NDQ5M2MyZDg2NmZiMWYyMDE3OGNlZWEwMjkzMzAyZmQzOTE0MGY0OWY2NGU2YzAwYjkxM2ZhMDRjMSJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.COAL)).setName("§aCapacité #1").setLore("§7Inflige aux " + Teams.SEEKER.getDisplayName() + "s un effet", "§7d'§8aveuglement §7pendant 5 secondes."), 1, 70, Integer.MAX_VALUE)}),
    LUCAS_SINCLAIR("Lucas Sinclair", "Lucas Charles Sinclair, interprété par Caleb McLaughlin, est un protagoniste majeur de Stranger Things. Il est le meilleur ami de Mike Wheeler, Dustin Henderson, Will Byers, Onze et Max Mayfield. ... Lucas a continué à aider ses amis et à combattre le Démogorgon jusqu'à ce qu'il soit tué par Onze.", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzljN2RhYmQ1MTBjNDE5OGU4OWFkNmEzOThlNTYzNjA2NGY4MTg1OGQ2YTc5ZGY3MDg3MTA4ZTJiNTZiMGU5YyJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.NETHER_STAR)).setName("§aCapacité #1").setLore("§7Applique un effet de §erayonnant §7aux", "§7" + Teams.SEEKER.getDisplayName() + "s pendant 15 secondes."), 1, 25, Integer.MAX_VALUE)}),
    ELEVEN("Eleven", "Eleven a été kidnappée et élevée au Laboratoire National d'Hawkins, où elle a été expérimentée pour ses capacités psychokinétiques héritées. Après s'être échappée du laboratoire, elle a été retrouvée par Mike, Lucas et Dustin.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjlkYmEwNzc1ZjQ3MDgxMWE5ZDRmZmIxMTkxZDE2NGQwYzM2Y2JjNzMyYzgyNWNlODQwYTg5MzUxZjExNGQzNCJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.SHULKER_SHELL)).setName("§aCapacité #1").setLore("§7Fait apparaître un mur §e3x3", "§7pendant 15 secondes."), 1, 60, Integer.MAX_VALUE)}),
    MAXINE_HARGROVE("Maxine Hargrove", "Maxine \"Max\" Mayfield, interprété par Sadie Sink, est un personnage majeur de la série Netflix Stranger Things. ... Après avoir brûlé le système de tunnel souterrain, Max fait officiellement partie du Parti et devient la petite amie de Lucas en l'embrassant au Bal d'Hiver.", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM2MzFkM2Y2ODAwNWIyYWI5YWE3ZjNkZGNlYjE1YzJmNTQ1YThhNjBmZjczNWM2MTVjMmNiZDE0MTNjNTViYSJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.QUARTZ)).setName("§aCapacité #1").setLore("§7Vous applique un effet de §bvitesse 2", "§7pendant 8 secondes."), 1, 0, 2)}),
    ROBIN_BUCKLEY("Robin Buckley", "Robin Buckley, interprété par Maya Hawke, est un protagoniste majeur introduit dans la troisième saison de Stranger Things. Elle a travaillé chez Scoops Ahoy situé dans le Centre commercial Starcourt aux côtés de Steve Harrington, en tant qu'ancienne camarade de classe de Steve et lors de l'été 1985.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjAzNWVjMWY1OTM2M2RjNGQ4MmE2ZjUwNjc4MjMyZGNhMzZiNWM1NjIyMzJiMDViYjU1YzkyNDk3ZTIwMTE0OSJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.LINGERING_POTION)).setBasePotionData(new PotionData(PotionType.SLOW_FALLING, false, false)).setName("§aCapacité #1"), 1, 0, Integer.MAX_VALUE), new ItemRole(new ItemBuilder(new ItemStack(Material.SPLASH_POTION)).setBasePotionData(new PotionData(PotionType.SLOW_FALLING)).setName("§aCapacité #2"), 2, 0, Integer.MAX_VALUE)}),
    STEVE_HARRINGTON("Steve Harrington", "En 1985, Steve trouve un emploi à Scoops Ahoy, dans le nouveau centre commercial Starcourt, où il travaille avec une ancienne camarade de classe, Robin Buckley. ... En collaboration avec Dustin, Robin et la jeune sœur de Lucas, Erica, Steve découvre que les Russes tentent de rouvrir un Portail vers l'Upside Down.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmYxZmY1MWEwYmY3ZjAzMDNmYWQ1MzUxZjljZjM4YjNhZDk3OTkzZDNiMTJiMGJiYWUzYWIyOWFmYTNiZWJlZSJ9fX0==", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.WOODEN_SHOVEL)).addEnchant(Enchantment.KNOCKBACK, 2).setUnbreakable(true).setName("§a§oBattes de Baseball"), 1, 0, Integer.MAX_VALUE)}),
    NANCY_WHEELER("Nancy Wheeler", "Nancy Wheeler, représentée par l'acteur principal Natalia Dyer, est l'un des deutéragonistes de Stranger Things. Au début, préoccupée par de simples problèmes de lycée chez les adolescentes, son monde est bouleversé lorsque sa meilleure amie Barbara Holland disparaît, l'encourageant à rechercher la vérité.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThjMzViYjBjZmM2MzdjOTQ5M2Y1MjY0OTUxMTRhOTMzZWUxMTY2ZWZkNWRiMTc5YTFkZTAzYzc5NDY1MGVjIn19fQ==", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.BOW)).setName("§aCapacité #1"), 1, 0, Integer.MAX_VALUE), new ItemRole(new ItemBuilder(new ItemStack(Material.SPECTRAL_ARROW, 5)).setName("§7Flèches spectrales"), 2, 0, Integer.MAX_VALUE), new ItemRole(new ItemBuilder(new ItemStack(Material.TIPPED_ARROW, 5)).setBasePotionData(new PotionData(PotionType.SLOWNESS, false, true)).setName("§7Flèches de lenteur"), 3, 0, Integer.MAX_VALUE)}),
    JONATHAN_BYERS("Jonathan Byers", "Jonathan Byers, interprété par l'acteur vedette Charlie Heaton, est un personnage majeur de Stranger Things. Il est le frère aîné de Will Byers. Sa vie a été bouleversée suite à la disparition de son jeune frère. ... Vers la fin, Jonathan aide sa mère et Nancy à libérer Will de Mind Flayer.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY4YjFkNmRkZGQ0NjYzZjc5YTRjNWNjNzVkZWZhNTVlM2JhYTVmODQzOGE3ODc4ODRjMmVhMWNlMzdiZGU5NiJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.ELYTRA)).setName("§aCapacité #1").setLore("§7Vous fais propulsé en hauteur", "§7de 15 blocs et vous équipe", "§7d'élytres pour que vous ne", "§7quittais pas ce monde."), 1, 15, 3)}),
    JIM_HOPPER("Jim Hopper", "James \"Jim\" Hopper, interprété par David Harbour, est un personnage majeur de Stranger Things. Hopper était chef de la police dans la petite ville endormie de Hawkins, dans l'Indiana, où il vivait une existence insouciante - pourtant hanté par un passé troublé.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc1MjBkOTMyNzU4NzliMGFjZjhmNGQyYWExYzc1NGM4NGQyYmI5MGUwNmMwN2NlZDQyMDQwODk1MTk0OTNhNiJ9fX0=", Teams.HIDER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.SPLASH_POTION)).setPotionColor(PotionEffectType.SLOW.getColor()).addPotionCustomEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 1)).setName("§aCapacité #1"), 1, 0, Integer.MAX_VALUE)}),

    BILLY_HARGROVE("Billy Hargrove", "William \"Billy\" Hargrove, interprété par Dacre Montgomery, est un personnage majeur de Stranger Things. ... Il était le demi-frère aîné de Max Mayfield et il a été montré qu'il faisait écho aux abus de son père dans son comportement envers elle et ses amis, ainsi qu'en s'en prenant à d'autres personnages.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFlOGQyNjA1MWZjYTEyZTI5ODE3ZDE1YzVjZjM0MDMzOWZkZDc1ZWUwZDRiZjM2MDI2ODgzYWEzNTVmN2I2YiJ9fX0=", Teams.SEEKER, new PotionEffect[]{}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.HEART_OF_THE_SEA)).setName("§cCaméra #1"), 2, 0, 1), new ItemRole(new ItemBuilder(new ItemStack(Material.HEART_OF_THE_SEA)).setName("§cCaméra #2"), 3, 0, 1), new ItemRole(new ItemBuilder(new ItemStack(Material.HEART_OF_THE_SEA)).setName("§cCaméra #3"), 4, 0, 1),}),
    DEMOGORGON("Demogorgon", "Le Démogorgon, également connu sous le nom \"le Monstre\" et \"Démogorgon de 1983\", était l'un des principaux antagonistes de la première saison de Stranger Things. ... La créature a finalement été vaincue a été vaincu le soir du 12 novembre 1983 détruite par Onze lors d'une confrontation dans une classe du Collège d'Hawkins.", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGYwMDNkNTk0NGQ0MzIxNTQ2ZmE1MmI0NTUyZDZmMjExNDU2MmExYmU4YWE3OWJjYTZjY2NlNTRlM2RiZGIzYiJ9fX0=", Teams.SEEKER, new PotionEffect[]{new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 0, false, false)}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.NETHERITE_INGOT)).setName("§cCapacité #1").setLore("§7Inflige aux " + Teams.HIDER.getDisplayName() + "s un", "§7effet de §2nausée §7et fait", "§7clignoté leurs écrans sur une durée", "§7de 15 secondes et vous applique", "§7un effet de §esauts améliorés", "§7pendant 10 secondes."), 2, 60, Integer.MAX_VALUE)}),
    MIND_FLAYER("Mind Flayer", "Cette créature monstrueuse servait de corps mandataire, à travers lequel Mind Flayer pouvait à nouveau influencer les événements de Hawkins. Plus tard, Mind Flayer a fait planter Billy dans sa voiture, ce qui l'a amené à enquêter sur les Steelworks, pour être capturé et devenir son hôte principal.", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGExYzVhMTQ4NWMwZmQ4NWYxNzlkZDliMDRhOWNkMjlmYTVmMzBlYWI3MGU5YTQ2NTc4MDBjMzk0YTc3ZDQ4NiJ9fX0=", Teams.SEEKER, new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false)}, new ItemRole[]{new ItemRole(new ItemBuilder(new ItemStack(Material.FIREWORK_STAR)).setName("§4Capacité #1").setLore("§7Inflige aux " + Teams.HIDER.getDisplayName().toLowerCase() + "s un effet", "§7de §clenteur 2 §7pendant 3 secondes", "§7et vous applique un effet", "§7de §bvitesse II §7pendant 10 secondes."), 2, 40, Integer.MAX_VALUE)});

    boolean enabled;
    final String displayName;
    final String description;
    final ItemStack head;
    final Teams team;
    final PotionEffect[] effects;
    final ItemRole[] items;
    Roles(final String displayName, final String description, final String textureValue, final Teams team, final PotionEffect[] effects, final ItemRole[] items) {
        this.enabled = true;

        this.displayName = displayName;
        this.description = description;
        this.head = new ItemBuilder(SkullCreator.create(textureValue)).setName(ChatColor.YELLOW + displayName).toItemStack();
        this.team = team;
        this.effects = effects;
        this.items = items;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getHead() {
        return head;
    }

    public Teams getTeam() {
        return team;
    }

    public PotionEffect[] getEffects() {
        return effects;
    }

    public ItemRole[] getItems() {
        return items;
    }

    public static List<Roles> getRoles() {
        return Arrays.stream(values()).filter(Roles::isEnabled).collect(Collectors.toList());
    }

    public static List<Roles> getDisabledRoles() {
        return Arrays.stream(values()).filter(e -> !e.isEnabled()).collect(Collectors.toList());
    }

    public static List<Roles> getRolesByTeam(final Teams team) {
        return getRoles().stream().filter(e -> e.getTeam() == team).collect(Collectors.toList());
    }
}
