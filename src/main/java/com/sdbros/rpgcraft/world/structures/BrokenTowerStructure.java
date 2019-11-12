package com.sdbros.rpgcraft.world.structures;

import com.mojang.datafixers.Dynamic;
import com.sdbros.rpgcraft.RpgCraft;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Function;

public class BrokenTowerStructure extends Feature<NoFeatureConfig> {

    private static final ResourceLocation BROKEN_TOWER_STRUCTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "structures/broken_tower");


    public BrokenTowerStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49878_1_) {
        super(p_i49878_1_);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig noFeatureConfig) {
        Random random = worldIn.getRandom();
        Rotation rotation = Rotation.NONE;
        TemplateManager templateManager = ((ServerWorld)worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplateDefaulted(BROKEN_TOWER_STRUCTURE);
        ChunkPos chunkPos = new ChunkPos(pos);
        MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(chunkPos.getXStart(), 0, chunkPos.getZStart(), chunkPos.getXEnd(), 256, chunkPos.getZEnd());
        PlacementSettings placementSettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableBoundingBox).setRandom(random).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        BlockPos blockPos = template.transformedSize(rotation);
        int xOffset = random.nextInt(16 - blockPos.getX());
        int zOffset = random.nextInt(16 - blockPos.getZ());
        int maxHeight = 256;

        int lvt_20_1_;
        for(lvt_20_1_ = 0; lvt_20_1_ < blockPos.getX(); ++lvt_20_1_) {
            for(int i = 0; i < blockPos.getZ(); ++i) {
                maxHeight = Math.min(maxHeight, worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + lvt_20_1_ + xOffset, pos.getZ() + i + zOffset));
            }
        }

        lvt_20_1_ = Math.max(maxHeight - 15 - random.nextInt(10), 10);
        BlockPos lvt_21_2_ = template.getZeroPositionWithTransform(pos.add(xOffset, lvt_20_1_, zOffset), Mirror.NONE, rotation);
        IntegrityProcessor lvt_22_1_ = new IntegrityProcessor(0.9F);
        placementSettings.clearProcessors().addProcessor(lvt_22_1_);
        template.addBlocksToWorld(worldIn, lvt_21_2_, placementSettings, 4);
        placementSettings.func_215220_b(lvt_22_1_);
        return true;
    }
}
