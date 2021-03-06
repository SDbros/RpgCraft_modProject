package com.sdbros.rpgcraft.client.renderer.entity;


import com.mojang.blaze3d.platform.GlStateManager;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.RedCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public final class RedCreeperRender extends MobRenderer<RedCreeperEntity, CreeperModel<RedCreeperEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/red_creeper.png");

    public RedCreeperRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CreeperModel<>(), 0.25F);
    }

    protected void preRenderCallback(RedCreeperEntity entitylivingbaseIn, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scalef(f2, f3, f2);
    }

    protected int getColorMultiplier(RedCreeperEntity entitylivingbaseIn, float lightBrightness, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        if ((int) (f * 10.0F) % 2 == 0) {
            return 0;
        } else {
            int i = (int) (f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(RedCreeperEntity entity) {
        return TEXTURE;
    }
}
