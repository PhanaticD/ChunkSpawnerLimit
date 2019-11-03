package com.phanaticmc.chunkspawnerlimit.commands;

import static com.phanaticmc.chunkspawnerlimit.ChunkSpawnerLimit.limit;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawnerlist")) {
			Bukkit.getWorlds().stream().forEach((w) -> {
				for (Chunk chunk : w.getLoadedChunks()) {
					int spawnercount = 0;
					for (BlockState block : chunk.getTileEntities()) {
						if (block instanceof CreatureSpawner) {
							spawnercount++;
							if (spawnercount > limit) {
								sender.sendMessage("Over Limit Spawner: " + block.getLocation().getBlockX() + " " + block.getLocation().getBlockY() + " " + block.getLocation().getBlockZ() + " " + block.getLocation().getWorld().getName());
							}
						}
					}
				}
			});
			return true;
		}
		return false;
	}
}
