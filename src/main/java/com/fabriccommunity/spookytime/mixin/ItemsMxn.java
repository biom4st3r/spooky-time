package com.fabriccommunity.spookytime.mixin;

import com.fabriccommunity.spookytime.registry.SpookyItems;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

@Mixin(Items.class)
public abstract class ItemsMxn
{
    @Shadow
    @Final
    public static Item CARVED_PUMPKIN;

    @Shadow
    @Final
    public static Item JACK_O_LANTERN;

    @Inject(at = @At("RETURN"),method="<clinit>")
    private static void statcInit(CallbackInfo ci)
    {
        CARVED_PUMPKIN = SpookyItems.CARVED_PUMPKIN;
        JACK_O_LANTERN = SpookyItems.JACK_O_LANTERN;
    }
    
    

}