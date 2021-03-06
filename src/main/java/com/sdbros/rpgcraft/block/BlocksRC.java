package com.sdbros.rpgcraft.block;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder(RpgCraft.MOD_ID)
public class BlocksRC {
    public static final UnstableMatterBlock UNSTABLE_MATTER = register("unstable_matter_block", new UnstableMatterBlock(Block.Properties.create(Material.FIRE, MaterialColor.BLACK).hardnessAndResistance(1.5F, 6.0F)));
    public static final RadioTowerBlock RADIO_TOWER = register("radio_tower", new RadioTowerBlock(Block.Properties.create(Material.IRON, MaterialColor.SNOW).hardnessAndResistance(1.0F, 3.0F)));
    public static final Block ACTIVATED_RUNITE_BLOCK = register("activated_runite_block", new Block(Block.Properties.create(Material.IRON, MaterialColor.SNOW).hardnessAndResistance(3.0F, 5.0F)));
    public static  final FloatingDimensionPortalBlock PORTAL_BLOCK = register("block_portal", new FloatingDimensionPortalBlock());

    private static <T extends Block> T register(String name, T block) {
        ModBlocks.BLOCKS_TO_REGISTER.put(name, block);
        return block;
    }
}
