package de.quantumrange.geometry;

import de.quantumrange.geometry.commands.ToolsCommand;
import de.quantumrange.geometry.listeners.BlockListener;
import de.quantumrange.geometry.models.Volume;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Geometry extends JavaPlugin {

	public static final List<Volume> areas = new ArrayList<>();
	private static Plugin plugin;

	@Override
	public void onEnable() {
		plugin = this;

		getCommand("createMesh").setExecutor(new ToolsCommand());

		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new ToolsCommand(), this);
		pluginManager.registerEvents(new BlockListener(), this);

		getServer().getScheduler().runTaskTimer(this, new ToolsCommand(), 5L, 5L);
	}

	@Override
	public void onDisable() {
		areas.clear();
		plugin = null;
	}

	public static Plugin getPlugin() {
		return plugin;
	}
}
