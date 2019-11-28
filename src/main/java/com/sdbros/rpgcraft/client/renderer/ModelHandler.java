package com.sdbros.rpgcraft.client.model;

import com.sdbros.rpgcraft.client.renderer.entity.RedCreeperRender;
import com.sdbros.rpgcraft.client.renderer.entity.ZombieVariantRender;
import com.sdbros.rpgcraft.entity.RedCreeperEntity;
import com.sdbros.rpgcraft.entity.ZombieVariantEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModelHandler {
    @OnlyIn(Dist.CLIENT)
    public static void registerModels(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ZombieVariantEntity.class, ZombieVariantRender::new);
        RenderingRegistry.registerEntityRenderingHandler(RedCreeperEntity.class, RedCreeperRender::new);
    }

}
