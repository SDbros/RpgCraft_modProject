package com.sdbros.rpgcraft.world.gen.features;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class FeatureManager {

    private static final Logger LOGGER = LogManager.getLogger(FeatureManager.class);
    private final static Map<Feature, RpgCraftTemplate> templates = Maps.newHashMap();

    public static void init() {
        LOGGER.debug("Loading feature");
        for (Feature s : Feature.values()) {
            loadTemplate(s);
        }
        LOGGER.debug("Loaded {} feature", Feature.values().length);
    }

    private static void loadTemplate(Feature feature) {
        InputStream input = FeatureManager.class.getResourceAsStream("/data/rpgcraft/structures/" + feature.name + ".nbt");
        if (input == null) {
            LOGGER.error("Failed to locate feature file {}", feature.name);
            return;
        }
        try {
            CompoundNBT data = CompressedStreamTools.readCompressed(input);
            RpgCraftTemplate template = new RpgCraftTemplate();
            template.read(data);
            templates.put(feature, template);


        } catch (IOException e) {
            LOGGER.error(String.format("Failed to load feature file %s", feature.name), e);
        }

    }
    @Nullable
    public static RpgCraftTemplate get(@Nonnull Feature s) {
        return templates.get(s);
    }

    public enum Feature {
        broken_tower("broken_tower", true);

        public static Set<String> getNames() {
            Set<String> names = Sets.newHashSet();
            for (Feature e : values()) {
                names.add(e.name);
            }
            return names;

        }
        String name;
        boolean loot;

        Feature(String name, boolean loot) {
            this.name = name;
            this.loot = loot;
        }
    }
}