package com.fabriccommunity.spookytime.mixin;

import java.util.ArrayList;

import com.fabriccommunity.spookytime.entity.goal.PumpkinTheGolem;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.entity.passive.VillagerEntity;

@Mixin(VillagerTaskListProvider.class)
public abstract class VillagerTaskListProviderMxn
{
    //PanicTask
    //Brain
    @Inject(at = @At("RETURN"), method =  "createIdleTasks",cancellable = true)
    private static void addIdleTask(CallbackInfoReturnable<ImmutableList<Pair<Integer, Task<VillagerEntity>>> > ci)
    {
        ArrayList<Pair<Integer,Task<VillagerEntity>>> newList = Lists.newArrayList(ci.getReturnValue());
        newList.add(Pair.of(7, new PumpkinTheGolem()));
        ci.setReturnValue(ImmutableList.copyOf(newList));
        
    }

}