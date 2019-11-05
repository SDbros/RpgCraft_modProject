package com.sdbros.rpgcraft.init;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.world.biome.ModBiome;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
    public static ModBiome MOD_BIOME;

    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        register( new ModBiome(), "mod_biome", 1024, Type.FOREST);
    }

    private static void register(Biome biome,String name, int weight, Type... types) {
        ResourceLocation id = RpgCraft.getId(name);
        biome.setRegistryName(id);
        ForgeRegistries.BIOMES.register(biome);
        BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biome, weight));
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }

}
