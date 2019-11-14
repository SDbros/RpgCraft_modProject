package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;


public final class ModFeatures {

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        // Features to register here
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        ResourceLocation id = RpgCraft.getId(name);
        feature.setRegistryName(id);
        ForgeRegistries.FEATURES.register(feature);
        return feature;
    }
}
