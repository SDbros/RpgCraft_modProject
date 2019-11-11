package com.sdbros.rpgcraft.init;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.world.biomes.MagicMountains;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
    public static MagicMountains MAGICMOUNTAINS;

    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        register(new MagicMountains(), "magic_mountains", 30, Type.FOREST);
    }

    private static void register(Biome biome, String name, int weight, Type... types) {
        ResourceLocation id = RpgCraft.getId(name);
        biome.setRegistryName(id);
        ForgeRegistries.BIOMES.register(biome);
        BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biome, weight));
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }

}
