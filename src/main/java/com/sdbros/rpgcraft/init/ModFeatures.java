package com.sdbros.rpgcraft.init;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.world.gen.structures.BrokenStructurePieces;
import com.sdbros.rpgcraft.world.gen.structures.BrokenStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModFeatures {

    public static final Map<String, Feature> FEATURES_TO_REGISTER = new LinkedHashMap<>();

    //structures
    public static final Structure<NoFeatureConfig> BROKEN_STRUCTURE = register("broken_structure", new BrokenStructure());
    public static final IStructurePieceType TOWER = IStructurePieceType.register(BrokenStructurePieces.Piece::new, "TOWE");
    public static final IStructurePieceType BUILDING = IStructurePieceType.register(BrokenStructurePieces.Piece::new, "BUIL");

    private ModFeatures(){}

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        // FeatureRC
        FEATURES_TO_REGISTER.forEach(ModFeatures::register);
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        ResourceLocation id = RpgCraft.getId(name);
        feature.setRegistryName(id);
        ForgeRegistries.FEATURES.register(feature);
        return feature;
    }
}
