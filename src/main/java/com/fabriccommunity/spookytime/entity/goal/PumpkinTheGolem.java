package com.fabriccommunity.spookytime.entity.goal;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fabriccommunity.spookytime.registry.SpookyItems;
import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.RayTraceContext.FluidHandling;
import net.minecraft.world.RayTraceContext.ShapeType;

public class PumpkinTheGolem extends Task<VillagerEntity> {

    static Logger logger = Logger.getLogger("biom4st3r");

    private IronGolemEntity reference;

    long starttime;
    static
    {
        logger.setLevel(Level.ALL);
        //PanicTask
        //net.minecraft.entity.ai.brain.task.FindInteractionTargetTask
    }

    public PumpkinTheGolem() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET,MemoryModuleState.VALUE_ABSENT   ,MemoryModuleType.LOOK_TARGET,MemoryModuleState.VALUE_ABSENT,MemoryModuleType.INTERACTION_TARGET,MemoryModuleState.VALUE_ABSENT));
        // PanicTask
        // VillagerEntity
        // GiveGiftsToHeroTask
        // HideInHomeTask
        // WanderAroundTask
    }

    private static ItemStack carvedpumpkin = new ItemStack(Items.CARVED_PUMPKIN,1);

    @Override
    protected void run(ServerWorld world, VillagerEntity villager, long long1) {
        logger.warning("Starting Ptg");
        //Brain<VillagerEntity> brain = villager.getBrain();
        Box box = new Box(villager.getBlockPos().add(-15 , -2, -15),villager.getBlockPos().add(15,2,15));
        List<IronGolemEntity> golems = (List<IronGolemEntity>)(Object)villager.world.getEntities(villager, box, (e -> e.getType() == EntityType.IRON_GOLEM));
        golems.removeIf(e -> !e.getEquippedStack(EquipmentSlot.HEAD).isEmpty());
        logger.warning(golems.size()+ "");
        Optional< IronGolemEntity> ig =  golems.stream().findAny();
        if(!ig.isPresent())
        {
            return;
        }
        RayTraceContext rtc = new RayTraceContext(villager.getPos().add(0, villager.getStandingEyeHeight(), 0), ig.get().getPos().add(0,ig.get().getStandingEyeHeight(),0), ShapeType.OUTLINE, FluidHandling.ANY, ig.get());
        if(world.rayTrace(rtc).getType() == Type.MISS)
        {
            reference = ig.get();
            LookTargetUtil.lookAtEachOther(villager, ig.get());
            villager.getBrain().putMemory(MemoryModuleType.INTERACTION_TARGET, ig.get());
            LookTargetUtil.walkTowards(villager, villager.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get(), 20);
            villager.setEquippedStack(EquipmentSlot.MAINHAND, carvedpumpkin.copy());
            return;
        }
        this.stop(world, villager, long1);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld_1, VillagerEntity villager, long long_1) {
        Optional<LivingEntity> target = villager.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET);
        logger.warning("is present "+ target.isPresent());
        if(target.isPresent())
        {
            double distance = villager.getPos().squaredDistanceTo(target.get().getPos());
            logger.warning("PTG distance to golem "+ distance);
            return distance < 20 * 20;
        }
        else if(reference != null)
        {
            double distance = villager.getPos().squaredDistanceTo(reference.getPos());
            
            logger.warning("PTG distance to golem "+ distance);
            return distance < 20 * 20;
        }
        return false;
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld_1, VillagerEntity villager, long long_1) {
        logger.warning("Finish Running PTG");
        reference = null;
        villager.getBrain().forget(MemoryModuleType.INTERACTION_TARGET);
        villager.getBrain().forget(MemoryModuleType.LOOK_TARGET);
        villager.setEquippedStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld_1, VillagerEntity villager, long long_1) {
        logger.warning("keepRunning");
        Optional<LivingEntity> target = villager.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET);
        if(target.isPresent())
        {
            if(villager.squaredDistanceTo(target.get()) < 3.5f)
            {
                logger.warning("PTG Placing Pumpkin");
                target.get().setEquippedStack(EquipmentSlot.HEAD, villager.getEquippedStack(EquipmentSlot.MAINHAND));
                this.stop(serverWorld_1, villager, long_1);
                return;
            }
            logger.warning("Giving chase! running to golem");
        }
        else if(reference != null)
        {
            if(villager.squaredDistanceTo(reference) < 3.5f)
            {
                logger.warning("PTG Placing Pumpkin");
                reference.setEquippedStack(EquipmentSlot.HEAD, new ItemStack(SpookyItems.CARVED_PUMPKIN));
                this.stop(serverWorld_1, villager, long_1);
            }
        }
    }
}