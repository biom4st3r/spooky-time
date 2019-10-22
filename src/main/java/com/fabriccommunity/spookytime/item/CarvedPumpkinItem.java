package com.fabriccommunity.spookytime.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class CarvedPumpkinItem extends BlockItem {

    public CarvedPumpkinItem() {
        super(Blocks.CARVED_PUMPKIN,new Settings().group(ItemGroup.BUILDING_BLOCKS));
    }

    @Override
    public Block getBlock() {
        return Blocks.CARVED_PUMPKIN;
    }

    @Override
    public boolean useOnEntity(ItemStack iS, PlayerEntity player, LivingEntity le,
            Hand hand) {
        if(le.getType() == EntityType.IRON_GOLEM && ((IronGolemEntity)le).getEquippedStack(EquipmentSlot.HEAD).isEmpty())
        {
            IronGolemEntity e = (IronGolemEntity) le;
            ItemStack headItem = iS.copy();
            headItem.setCount(1);
            e.setEquippedStack(EquipmentSlot.HEAD, headItem);
            iS.decrement(1);
            return true;
        }
        return super.useOnEntity(iS, player, le, hand);
    }

}