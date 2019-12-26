package com.sdbros.rpgcraft.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.CloudSlimeEntity;
import com.sdbros.rpgcraft.entity.ClusterCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeGelLayer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public final class CloudSlimeRender extends MobRenderer<CloudSlimeEntity, SlimeModel<CloudSlimeEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/cloud_slime.png");
    private static final ResourceLocation WET_TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/cloud_slime_wet.png");

    public CloudSlimeRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SlimeModel<>(16), 0.25F);
        this.addLayer(new SlimeGelLayer<>(this));

    }

    public void doRender(CloudSlimeEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.shadowSize = 0.25F * (float)entity.getCloudSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected void preRenderCallback(CloudSlimeEntity entitylivingbaseIn, float partialTickTime) {
        float f = 0.999F;
        GlStateManager.scalef(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getCloudSlimeSize();
        float f2 = MathHelper.lerp(partialTickTime, entitylivingbaseIn.prevSquishFactor, entitylivingbaseIn.squishFactor) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scalef(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }


    protected ResourceLocation getEntityTexture(CloudSlimeEntity entity) {
        return entity.isWet() ? WET_TEXTURE : TEXTURE;
    }
}
