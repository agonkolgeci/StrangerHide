package fr.jielos.strangerhide.managers;

import fr.jielos.strangerhide.listeners.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenersManager {

	final JavaPlugin instance;
	public ListenersManager(final JavaPlugin instance) {
		this.instance = instance;
	}
	
	public void register() {
		final PluginManager pluginManager = instance.getServer().getPluginManager();

		pluginManager.registerEvents(new PlayerJoin(), instance);
		pluginManager.registerEvents(new PlayerQuit(), instance);
		pluginManager.registerEvents(new PlayerLogin(), instance);
		pluginManager.registerEvents(new PlayerMove(), instance);
		pluginManager.registerEvents(new PlayerInteract(), instance);
		pluginManager.registerEvents(new PlayerInteractEntity(), instance);
		pluginManager.registerEvents(new PlayerUnleashedEntity(), instance);
		pluginManager.registerEvents(new PlayerSwapHandItems(), instance);
		pluginManager.registerEvents(new PlayerArmorStandManipulate(), instance);
		pluginManager.registerEvents(new PlayerItemDrop(), instance);
		
		pluginManager.registerEvents(new AsyncPlayerChat(), instance);
		
		pluginManager.registerEvents(new EntityDamage(), instance);
		pluginManager.registerEvents(new EntityDamageByEntity(), instance);

		pluginManager.registerEvents(new InventoryClick(), instance);

		pluginManager.registerEvents(new HangingBreakByEntity(), instance);
		pluginManager.registerEvents(new CreatureSpawn(), instance);
		pluginManager.registerEvents(new FoodLevelChange(), instance);
		pluginManager.registerEvents(new VehicleDestroy(), instance);
	}
	
}