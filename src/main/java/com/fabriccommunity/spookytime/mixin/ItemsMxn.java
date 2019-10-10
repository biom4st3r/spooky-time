package com.fabriccommunity.spookytime.mixin;

import com.fabriccommunity.spookytime.item.CarvedPumpkinItem;
import com.fabriccommunity.spookytime.item.JackoLanternItem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(Items.class)
public abstract class ItemsMxn
{
    @Shadow
    @Final
    public static Item CARVED_PUMPKIN;

    @Shadow
    protected static Item register(Block block_1, Item item_1) {
        return null;
    }

    @Inject(at = @At("RETURN"),method="<clinit>")
    private static void statcInit(CallbackInfo ci)
    {
        Registry.ITEM.set(Registry.ITEM.getRawId(Items.CARVED_PUMPKIN), Registry.ITEM.getId(Items.CARVED_PUMPKIN), new CarvedPumpkinItem());
        Registry.ITEM.set(Registry.ITEM.getRawId(Items.JACK_O_LANTERN), Registry.ITEM.getId(Items.JACK_O_LANTERN), new JackoLanternItem());
    }
    
    

}