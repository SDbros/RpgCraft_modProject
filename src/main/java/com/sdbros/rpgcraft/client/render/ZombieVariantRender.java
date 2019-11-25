package com.sdbros.rpgcraft.client.render;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.ZombieVariantEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;

public class ZombieVariantRender extends MobRenderer<ZombieVariantEntity, ZombieVariantModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/zombie_variant.png");

        public ZombieVariantRender(EntityRendererManager renderManagerIn) {
            super(renderManagerIn, new ZombieVariantModel(), 0.25F);
        }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(ZombieVariantEntity entity) {
        return TEXTURE;
    }

}
