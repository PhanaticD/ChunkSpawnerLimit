package com.phanaticmc.chunkspawnerlimit.listeners;

import com.phanaticmc.chunkspawnerlimit.utils;

import static com.phanaticmc.chunkspawnerlimit.ChunkSpawnerLimit.*;
import static org.bukkit.Bukkit.getServer;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChunkLoad implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChunkLoad(ChunkLoadEvent event) {
		if (event.isNewChunk()) {
			return;
		}
		Chunk chunk = event.getChunk();
		int spawnercount = 0;
		for (BlockState block : chunk.getTileEntities()) {
			if (block.getType() == spawnermat) {
				spawnercount++;
				if (spawnercount > limit) {
					if (cleanOnChunkLoad) {
						ItemStack drop = new ItemStack(spawnermat);
						CreatureSpawner existing = (CreatureSpawner) block;
						utils.setSpawnerMob(drop, existing.getSpawnedType());
						ItemMeta itemMeta = drop.getItemMeta();
						String displayname = existing.getSpawnedType().name();
						itemMeta.setDisplayName(ChatColor.RESET + displayname.substring(0, 1).toUpperCase() + displayname.substring(1).toLowerCase() + " Spawner");
						drop.setItemMeta(itemMeta);
						chunk.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5), drop);
						block.setType(Material.AIR);
						block.update(true);
					}
					if (notifyOnChunkLoad) {
						getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> {
							chunk.getWorld().getNearbyEntities(new Location(chunk.getWorld(), chunk.getX() * 16, 64, chunk.getZ() * 16), 100, 100, 100).stream().filter((ent) -> (ent instanceof Player)).map((ent) -> (Player) ent).forEach((player) -> {
								player.sendMessage("Too many Spawners in this chunk, " + limit + " is the max! x:" + block.getLocation().getBlockX() + " y:" + block.getLocation().getBlockY() + " z:" + block.getLocation().getBlockZ());
							});
						}, 20L);
					}
				}
			}
		}
	}
}
