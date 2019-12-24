package com.sdbros.rpgcraft.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.mobs.CrazedSummonerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class CrazedSummonerRender extends MobRenderer<CrazedSummonerEntity, IllagerModel<CrazedSummonerEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/crazed_summoner.png");
    private final float scale;


    public CrazedSummonerRender(EntityRendererManager rendererManager) {
        super(rendererManager, new IllagerModel(0.0F, 0.0F, 64, 64), 0.5F * 1.3F);
        this.scale = 1.3F;

        //hold bow
        this.addLayer(new HeldItemLayer<CrazedSummonerEntity, IllagerModel<CrazedSummonerEntity>>(this) {
            public void render(CrazedSummonerEntity entity, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
                if (entity.isSpellcasting() || entity.isAggressive()) {
                    super.render(entity, var1, var2, var3, var4, var5, var6, var7);
                }
            }
        });

        //show hat
        this.entityModel.func_205062_a().showModel = true;
    }

//    public void doRender(CrazedSummonerEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
//        super.doRender(entity, x, y, z, entityYaw, partialTicks);
//    }

    @Override
    protected void preRenderCallback(CrazedSummonerEntity entity, float partialTickTime) {
        GlStateManager.scalef(scale, scale, scale);
    }

    protected ResourceLocation getEntityTexture(CrazedSummonerEntity crazedSummonerEntity) {
        return TEXTURE;
    }

}
