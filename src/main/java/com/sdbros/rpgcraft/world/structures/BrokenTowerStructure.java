package com.sdbros.rpgcraft.world.structures;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class BrokenTowerStructure extends Structure<NoFeatureConfig> {


    public BrokenTowerStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean hasStartAt(ChunkGenerator<?> generator, Random random, int chunkX, int chunkZ) {
        ChunkPos pos = this.getStartPositionForPosition(generator, random, chunkX, chunkZ, 0, 0);
        if (chunkX == pos.x && chunkZ == pos.z) {
            Biome biome = generator.getBiomeProvider().getBiome(new BlockPos(chunkX * 16 + 9, 0, chunkZ * 16 + 9));
            return generator.hasStructure(biome, this);
        } else {
            return false;
        }
    }

    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    @Override
    public String getStructureName() {
        return "rpgcraft:broken_tower";
    }

    @Override
    public int getSize() {
        return 1;
    }

    public static class Start extends MarginedStructureStart {

        public Start(Structure<?> structure, int chunkPosX, int chunkPosZ, Biome biome, MutableBoundingBox bounds, int reference, long seed) {
            super(structure, chunkPosX, chunkPosZ, biome, bounds, reference, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {

           // System.out.println("GENERATE TOWER"); //breakpoint here
            this.recalculateStructureSize();
        }

    }


}

