package com.sdbros.rpgcraft.world.gen.features;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.world.gen.feature.template.Template;
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
    private final static Map<Feature, Template> templates = Maps.newHashMap();

    public static void init() {
        LOGGER.debug("Loading feature");
        for (Feature f : Feature.values()) {
            loadTemplate(f);
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
            Template template = new Template();
            template.read(data);
            templates.put(feature, template);

        } catch (IOException e) {
            LOGGER.error(String.format("Failed to load feature file %s", feature.name), e);
        }
    }

    @Nullable
    public static Template get(@Nonnull Feature f) {
        return templates.get(f);
    }

    //name and value need to be the same
    public enum Feature {
        broken_tower("broken_tower"),
        broken_building("broken_building");

        public static Set<String> getNames() {
            Set<String> names = Sets.newHashSet();
            for (Feature e : values()) {
                names.add(e.name);
            }
            return names;
        }

        String name;

        Feature(String name) {
            this.name = name;
        }
    }
}