package com.phanaticmc.chunkspawnerlimit.listeners;

import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import static com.phanaticmc.chunkspawnerlimit.ChunkSpawnerLimit.*;

public class BlockPlace implements Listener {
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlockPlaced().getType() != spawnermat) {
			return;
		}
		Chunk chunk = event.getBlock().getLocation().getChunk();
		int spawnercount = 1;
		for (BlockState block : chunk.getTileEntities()) {
			if (block.getType() == spawnermat) {
				spawnercount++;
				if (spawnercount > limit) {
					event.getPlayer().sendMessage("Too many Spawners in this chunk, " + limit + " is the max!");
					event.setCancelled(true);
					break;
				}
			}
		}
	}
}
