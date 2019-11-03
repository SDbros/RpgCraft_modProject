package com.sdbros.rpgcraft.world;

import com.sdbros.rpgcraft.block.Ores;
import com.sdbros.rpgcraft.world.feature.RCOreFeature;
import com.sdbros.rpgcraft.world.feature.RCOreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Predicate;


/**
 * Experimental world generation. Not sure if Forge intends to add something, but this should work
 * for now.
 */
public final class ModWorldFeatures {
    private static final EnumMap<Ores, Set<ResourceLocation>> RPGCRAFT_BIOMES = new EnumMap<>(Ores.class);

    private ModWorldFeatures() {
    }

    public static void addFeaturesToBiomes() {
        EnumSet<Ores> selected = EnumSet.noneOf(Ores.class);

        for (Biome biome : ForgeRegistries.BIOMES) {
            long seed = getBiomeSeed(biome);

            Collection<Ores> toAdd = EnumSet.noneOf(Ores.class);
            for (int i = 0; toAdd.size() < Math.abs(seed % 3) + 3 && i < 100; ++i) {
            }

            addCopperOre(biome);
        }
    }

    private static void addCopperOre(Biome biome) {
        addOre(biome, Ores.COPPER.getOreBlock(), 6, 200, 6, 28, d -> true);
        System.out.print("COPPER GERERATED");
    }


    private static void addOre(Biome biome, Block block, int size, int count, int minHeight, int maxHeight, Predicate<DimensionType> dimension) {
        addOre(biome, block, size, count, minHeight, maxHeight, s -> s.isIn(Tags.Blocks.STONE), dimension);
    }

    private static void addOre(Biome biome, Block block, int size, int count, int minHeight, int maxHeight, Predicate<BlockState> blockToReplace, Predicate<DimensionType> dimension) {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
                RCOreFeature.INSTANCE,
                new RCOreFeatureConfig(
                        block.getDefaultState(),
                        size,
                        blockToReplace,
                        dimension
                ),
                Placement.COUNT_RANGE,
                new CountRangeConfig(count, minHeight, 0, maxHeight)
        ));
    }

    private static long getBaseSeed() {
        // Default value is based on PC username
        String username = System.getProperty("user.name");
        if (username == null || username.isEmpty()) {
            // Fallback value
            return ModList.get().size() * 10000;
        }
        return username.hashCode();
    }

    private static long getBiomeSeed(Biome biome) {
        return getBaseSeed()
                + Objects.requireNonNull(biome.getRegistryName()).toString().hashCode()
                + biome.getCategory().ordinal() * 100
                + biome.getPrecipitation().ordinal() * 10
                + biome.getTempCategory().ordinal();
    }
}
