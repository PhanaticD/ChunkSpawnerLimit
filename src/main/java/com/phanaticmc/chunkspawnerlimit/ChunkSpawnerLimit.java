package com.phanaticmc.chunkspawnerlimit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import com.phanaticmc.chunkspawnerlimit.listeners.*;
import com.phanaticmc.chunkspawnerlimit.commands.*;
import org.bukkit.configuration.file.FileConfiguration;

public class ChunkSpawnerLimit extends JavaPlugin {
	public static ChunkSpawnerLimit instance;
	public static int limit;
	public static boolean cleanOnChunkLoad;
	public static boolean notifyOnChunkLoad;
	public static Material spawnermat;

	@Override
	public void onEnable() {
		instance = this;

		FileConfiguration config = getConfig();
		if (!config.contains("limit")) {
			config.addDefault("limit", 4);
		}
		if (!config.contains("cleanOnChunkLoad")) {
			config.addDefault("cleanOnChunkLoad", false);
		}
		if (!config.contains("notifyOnChunkLoad")) {
			config.addDefault("notifyOnChunkLoad", false);
		}
		config.options().copyDefaults(true);
		saveConfig();

		limit = config.getInt("limit");
		cleanOnChunkLoad = config.getBoolean("cleanOnChunkLoad");
		notifyOnChunkLoad = config.getBoolean("notifyOnChunkLoad");

		if (cleanOnChunkLoad || notifyOnChunkLoad) {
			Bukkit.getPluginManager().registerEvents(new ChunkLoad(), this);
		}
		Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
		getServer().getPluginCommand("spawnerlist").setExecutor(new ListCommand());
		getServer().getPluginCommand("spawnerdelete").setExecutor(new DeleteCommand());

		setSpawnermat();
	}

	private void setSpawnermat() {
		for (Material mat : Material.values()) {
			if (mat.toString().endsWith("SPAWNER")) {
				spawnermat = mat;
				break;
			}
		}
	}
}
