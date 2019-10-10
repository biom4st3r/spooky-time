package com.fabriccommunity.spookytime.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.entity.passive.IronGolemEntity;

@Mixin(IronGolemEntityRenderer.class)
public abstract class IronGolemRendererMxn extends MobEntityRenderer<IronGolemEntity,IronGolemEntityModel<IronGolemEntity>> {

    public IronGolemRendererMxn(EntityRenderDispatcher entityRenderDispatcher,
            IronGolemEntityModel<IronGolemEntity> ironGolemModel, float scale) {
        super(entityRenderDispatcher, ironGolemModel, scale);
    }
    
    @SuppressWarnings({"unchecked","rawtypes"})
    @Inject(at = @At("RETURN"), method = "<init>")
    public void init(CallbackInfo ci)
    {
        this.addFeature(new HeadFeatureRenderer(this));
    }

}