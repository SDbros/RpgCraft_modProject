package com.sdbros.rpgcraft.init;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.world.structures.FeaturesRC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModFeatures {

    public static final Map<String, Feature> FEATURES_TO_REGISTER = new LinkedHashMap<>();

    private ModFeatures(){
    }

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {

        // FeatureRC
        new FeaturesRC();
        FEATURES_TO_REGISTER.forEach(ModFeatures::register);
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        ResourceLocation id = RpgCraft.getId(name);
        feature.setRegistryName(id);
        ForgeRegistries.FEATURES.register(feature);
        return feature;
    }
}
