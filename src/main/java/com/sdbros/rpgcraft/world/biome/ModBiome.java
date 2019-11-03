package com.sdbros.rpgcraft.world.biome;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.Ores;
import com.sdbros.rpgcraft.init.ModEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;


public class ModBiome extends Biome {
    public ModBiome() {
        super((new Biome.Builder())
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Ores.COPPER.getStorageBlock().getDefaultState(), Ores.COPPER.getOreBlock().getDefaultState(), Blocks.LAPIS_BLOCK.getDefaultState())))
                .precipitation(RainType.RAIN)
                .category(Category.NETHER)
                .downfall(0.2f)
                .depth(1.4f)
                .scale(1.3f)
                .temperature(0.7f)
                .waterColor(0x34eb67)
                .waterFogColor(0x34eb67)
                .parent(null));

        DefaultBiomeFeatures.addIcebergs(this);
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addOres(this);


        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(ModEntities.RED_CREEPER.getValue(), 100, 1, 5));

        ResourceLocation id = RpgCraft.getId("mod_biome");
        this.setRegistryName(id);
    }
}
