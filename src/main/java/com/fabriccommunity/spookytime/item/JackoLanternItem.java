package com.fabriccommunity.spookytime.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class JackoLanternItem extends BlockItem {

    public JackoLanternItem() {
        super(Blocks.JACK_O_LANTERN, new Settings().group(ItemGroup.BUILDING_BLOCKS));
        // TODO Auto-generated constructor stub
    }

    @Override
    public Block getBlock() {
        return Blocks.JACK_O_LANTERN;
    }

    @Override
    public boolean useOnEntity(ItemStack iS, PlayerEntity player, LivingEntity le,
            Hand hand_1) {
        if(le.getType() == EntityType.IRON_GOLEM && ((IronGolemEntity)le).getEquippedStack(EquipmentSlot.HEAD).isEmpty())
        {
            IronGolemEntity e = (IronGolemEntity) le;
            ItemStack headItem = iS.copy();
            headItem.setCount(1);
            e.setEquippedStack(EquipmentSlot.HEAD, headItem);
            iS.decrement(1);
            return true;
        }
        return super.useOnEntity(iS, player, le, hand_1);
    }
}