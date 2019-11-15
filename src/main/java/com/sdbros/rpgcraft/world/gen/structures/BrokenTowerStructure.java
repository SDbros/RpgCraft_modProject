package com.sdbros.rpgcraft.world.gen.structures;

import com.mojang.datafixers.Dynamic;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class BrokenTowerStructure extends ScatteredStructure<NoFeatureConfig> {

    public BrokenTowerStructure() {
        super(NoFeatureConfig::deserialize);
    }

    @Nonnull
    @Override
    public String getStructureName() {
        return "Broken_Tower";
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Nonnull
    @Override
    public Structure.IStartFactory getStartFactory() {
        return BrokenTowerStructure.Start::new;
    }

    @Override
    protected int getSeedModifier() {
        return 14357719;
    }

    public static class Start extends StructureStart {
        Start(Structure<?> structure, int chunkX, int chunkZ, Biome biomeIn, MutableBoundingBox boundsIn, int referenceIn, long seed) {
            super(structure, chunkX, chunkZ, biomeIn, boundsIn, referenceIn, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            NoFeatureConfig nofeatureconfig = (NoFeatureConfig)generator.getStructureConfig(biomeIn, ModFeatures.BROKEN_TOWER);
            int i = chunkX * 16;
            int j = chunkZ * 16;
            BlockPos blockpos = new BlockPos(i, 90, j);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            BrokenTowerPieces.init(templateManagerIn, blockpos, rotation, this.components, this.rand, nofeatureconfig);
            this.recalculateStructureSize();
        }
    }
}
