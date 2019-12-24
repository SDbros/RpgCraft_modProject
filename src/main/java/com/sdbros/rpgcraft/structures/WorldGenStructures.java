package com.sdbros.rpgcraft.structures;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.sdbros.rpgcraft.config.ConfigGeneral;
import com.sdbros.rpgcraft.config.ConfigStructures;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGenStructures implements IWorldGenerator {

    private static List<Biome> biomeList = (List<Biome>) ForgeRegistries.BIOMES.getValues();


    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, AbstractChunkProvider chunkProvider) {
        if (world.getWorldInfo().isMapFeaturesEnabled() && world.getWorldType() != WorldType.FLAT) {
            int blockX = chunkX * 16 + random.nextInt(16);            //(chunkZ * 16) + random.nextInt(15)   OLD
            int blockZ = chunkZ * 16 + random.nextInt(16);

            if (world.getDimension().getType() == DimensionType.THE_END) {
                if (ConfigGeneral.activateEndGeneration) {
                    //End
                }
            } else if (world.getDimension().getType() == DimensionType.THE_NETHER) {
                if (ConfigGeneral.activateNetherGeneration) {
                    //Nether
                }
            } else if (ConfigGeneral.additionalDimensions.length > 0) {
                for (int i = 0; i < ConfigGeneral.additionalDimensions.length; i++) {
                    int dimension = ConfigGeneral.additionalDimensions[i];

                    if (world.getDimension().getType().getId() == dimension) {

                        if (ConfigGeneral.activateOverworldGeneration) {
                            generateDownsetStructure(StructureList.BROKEN_BUILDING, world, random, blockX, blockZ, ConfigStructures.spawnchanceBiggerBrokenBuilding, BiomeDictionary.getBiomes(Type.SPOOKY), chunkGenerator);
                            generateDownsetStructure(StructureList.BROKEN_TOWER, world, random, blockX, blockZ, ConfigStructures.spawnchanceBrokenTower, BiomeDictionary.getBiomes(Type.SPOOKY), chunkGenerator);
                        }
                    }
                }
            }
        }
    }


    private void generateStructure(Feature<NoFeatureConfig> feature, IWorld world, Random random, int blockX, int blockZ, int chance, Set<Biome> set, ChunkGenerator<net.minecraft.world.gen.GenerationSettings> chunkGenerator) {
        int chanceModified = random.nextInt((int) Math.max(ConfigStructures.generationModifier * chance, 1.0f));

        int blockY = StructureGenerator.getGroundFromAbove(world, blockX, blockZ);
        BlockPos pos = new BlockPos(blockX, blockY + 1, blockZ);

        Biome biome = world.getChunk(pos).getBiome(pos);


        if (set.contains(biome)) {
            if (chanceModified == 0) {
                feature.place(world, chunkGenerator, random, pos, NoFeatureConfig.NO_FEATURE_CONFIG);
            }
        }
    }


    private void generateDownsetStructure(Feature<NoFeatureConfig> feature, IWorld world, Random random, int blockX, int blockZ, int chance, Set<Biome> set, ChunkGenerator chunkGenerator) {
        int chanceModified = random.nextInt((int) Math.max(ConfigStructures.generationModifier * chance, 1.0f));

        int blockY = StructureGenerator.getGroundFromAbove(world, blockX, blockZ);
        BlockPos pos = new BlockPos(blockX, blockY, blockZ);

        Biome biome = world.getChunk(pos).getBiome(pos);

        if (set.contains(biome)) {
            if (chanceModified == 0) {
                feature.place(world, chunkGenerator, random, pos, NoFeatureConfig.NO_FEATURE_CONFIG);
            }
        }
    }


    private void generateBuryStructure(Feature<NoFeatureConfig> feature, IWorld world, Random random, int blockX, int blockZ, int chance, Set<Biome> set, ChunkGenerator<net.minecraft.world.gen.GenerationSettings> chunkGenerator) {
        int chanceModified = random.nextInt((int) Math.max(ConfigStructures.generationModifier * chance, 1.0f));

        int blockY = StructureGenerator.getGroundFromAbove(world, blockX, blockZ);
        BlockPos pos = new BlockPos(blockX, blockY - 2, blockZ);

        Biome biome = world.getChunk(pos).getBiome(pos);

        if (set.contains(biome)) {
            if (chanceModified == 0) {
                feature.place(world, chunkGenerator, random, pos, NoFeatureConfig.NO_FEATURE_CONFIG);
            }
        }
    }


    private void generateUndergroundStructure(Feature<NoFeatureConfig> feature, IWorld world, Random random, int blockX, int blockZ, int chance, Set<Biome> set, ChunkGenerator<net.minecraft.world.gen.GenerationSettings> chunkGenerator) {
        int chanceModified = random.nextInt((int) Math.max(ConfigStructures.generationModifier * chance, 1.0f));

        int blockY = StructureGenerator.getGroundFromAbove(world, blockX, blockZ);
        BlockPos pos = new BlockPos(blockX, (int) (Math.random() * ((blockY - 20 - 25) + 1)) + 25, blockZ);

        Biome biome = world.getChunk(pos).getBiome(pos);

        if (set.contains(biome)) {
            if (chanceModified == 0) {
                feature.place(world, chunkGenerator, random, pos, NoFeatureConfig.NO_FEATURE_CONFIG);
            }
        }
    }


    private void generateFlyingStructure(Feature<NoFeatureConfig> feature, IWorld world, Random random, int blockX, int blockZ, int chance, Set<Biome> set, ChunkGenerator<net.minecraft.world.gen.GenerationSettings> chunkGenerator) {
        int chanceModified = random.nextInt((int) Math.max(ConfigStructures.generationModifier * chance, 1.0f));

        int blockY = StructureGenerator.getGroundFromAbove(world, blockX, blockZ);
        BlockPos pos = new BlockPos(blockX, random.nextInt(230) + blockY + 35, blockZ);

        Biome biome = world.getChunk(pos).getBiome(pos);

        if (set.contains(biome)) {
            if (chanceModified == 0) {
                feature.place(world, chunkGenerator, random, pos, NoFeatureConfig.NO_FEATURE_CONFIG);
            }
        }
    }
}
