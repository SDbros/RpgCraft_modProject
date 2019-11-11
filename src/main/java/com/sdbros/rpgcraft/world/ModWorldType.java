package com.sdbros.rpgcraft.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

import static com.sdbros.rpgcraft.init.ModBiomes.MAGICMOUNTAINS;

public class ModWorldType extends WorldType {
    public ModWorldType() {
        super("mod_type");
    }


    @Override
    public ChunkGenerator<?> createChunkGenerator(World world) {
        OverworldGenSettings settings = new OverworldGenSettings();
        SingleBiomeProviderSettings single = new SingleBiomeProviderSettings();
        single.setBiome(MAGICMOUNTAINS);
        return new OverworldChunkGenerator(world, new SingleBiomeProvider(single), settings);
    }
}
