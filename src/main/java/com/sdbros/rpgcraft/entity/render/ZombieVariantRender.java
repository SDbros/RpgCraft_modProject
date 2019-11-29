package com.sdbros.rpgcraft.entity.render;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.mobs.ZombieVariantEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ZombieVillagerModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ZombieVariantRender extends MobRenderer<ZombieVariantEntity, ZombieVillagerModel<ZombieVariantEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/zombie_variant.png");

    public ZombieVariantRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZombieVillagerModel<>(), 0.25F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(ZombieVariantEntity entity) {
        return TEXTURE;
    }

}
