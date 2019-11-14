package com.sdbros.rpgcraft.world.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.*;
import java.util.Random;
import java.util.function.Function;

import static com.sdbros.rpgcraft.world.features.FeatureManager.Feature.BROKEN_TOWER;


public class BrokenTowerFeature extends Feature<NoFeatureConfig> {

    public BrokenTowerFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49878_1_) {
        super(p_i49878_1_);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig noFeatureConfig) {

        Rotation rotation = Rotation.NONE;
        RpgCraftTemplate template = FeatureManager.get(BROKEN_TOWER);
        ChunkPos chunkPos = new ChunkPos(pos);
        MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(chunkPos.getXStart(), 0, chunkPos.getZStart(), chunkPos.getXEnd(), 256, chunkPos.getZEnd());
        PlacementSettings placementSettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableBoundingBox).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        BlockPos blockPos = template.transformedSize(rotation);
        BlockPos transform = template.getZeroPositionWithTransform(pos.add(blockPos.getX(), 150, blockPos.getZ()), Mirror.NONE, rotation);
        template.addBlocksToWorld(worldIn, transform, placementSettings, 4);
        return true;
    }
}
