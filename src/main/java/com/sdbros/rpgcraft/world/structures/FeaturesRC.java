package com.sdbros.rpgcraft.world.structures;

import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("rpgcraft")
public class FeaturesRC {

    public static final BrokenTowerStructure BROKEN_TOWER = register("broken_tower", new BrokenTowerStructure(NoFeatureConfig::deserialize));

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        ModFeatures.FEATURES_TO_REGISTER.put(name, feature);
        return feature;
    }
}
