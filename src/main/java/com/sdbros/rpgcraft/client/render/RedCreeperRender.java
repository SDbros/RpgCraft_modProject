package com.sdbros.rpgcraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entities.RedCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nonnull;

public final class RedCreeperRender extends MobRenderer<RedCreeperEntity, RedCreeperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/red_creeper.png");

    public RedCreeperRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new RedCreeperModel(), 0.25F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call
     * Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull RedCreeperEntity entity) {
        return TEXTURE;
    }
}
