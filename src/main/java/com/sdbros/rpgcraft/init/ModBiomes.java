package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.world.biomes.FloatingMagicMountains;
import com.sdbros.rpgcraft.world.biomes.MagicMountains;
import com.sdbros.rpgcraft.world.dimension.unstable.UnstableBiomeProvider;
import com.sdbros.rpgcraft.world.dimension.unstable.UnstableChunkGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {

    //Biomes
    public static MagicMountains MAGICMOUNTAINS;
    public static FloatingMagicMountains FLOATINGMAGICMOUNTAINS;

    //ChunkGenerator
    public static ChunkGeneratorType<GenerationSettings, UnstableChunkGenerator> generatorType = new ChunkGeneratorType<>(UnstableChunkGenerator::new, false, GenerationSettings::new);

    //BiomeProvider
    public static BiomeProviderType<SingleBiomeProviderSettings, UnstableBiomeProvider> biomeProviderType = new BiomeProviderType<>(UnstableBiomeProvider::new, SingleBiomeProviderSettings::new);

    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        register(new MagicMountains(), "magic_mountains", 30, Type.FOREST);
        register(new FloatingMagicMountains(), "floating_magic_mountains", 0, Type.END);
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
