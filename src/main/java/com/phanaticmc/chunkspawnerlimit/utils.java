package com.phanaticmc.chunkspawnerlimit;

import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class utils {
    public static ItemStack setSpawnerMob(ItemStack is, EntityType type) {
        BlockStateMeta bsm = (BlockStateMeta) is.getItemMeta();
        BlockState bs = bsm.getBlockState();
        ((CreatureSpawner) bs).setSpawnedType(type);
        bsm.setBlockState(bs);
        is.setItemMeta(bsm);
        return (is);
    }
}
