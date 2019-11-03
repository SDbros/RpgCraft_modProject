package com.sdbros.rpgcraft.init;


import com.sdbros.rpgcraft.world.biome.ModBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;


public class ModBiomes {
    public static ModBiome MOD_BIOME;

    public static void registerBiomes() {
        register(MOD_BIOME, Type.MAGICAL);
    }

    private static void register(Biome biome, Type... types) {
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }

}
