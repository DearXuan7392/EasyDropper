package com.dearxuan.easydropper.mixin;

import com.dearxuan.easydropper.Config.ModConfig;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDispenserBehavior.class)
public class EasyItemDispenserBehaviorMixin {

    @Inject(
            method = {"spawnItem"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private static void spawnItem(
            World world,
            ItemStack stack,
            int speed, // 默认是 6
            Direction side,
            Position pos,
            CallbackInfo info){
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        if (side.getAxis() == Direction.Axis.Y) {
            e -= 0.125;
        } else {
            e -= 0.15625;
        }
        ItemEntity itemEntity = new ItemEntity(world, d, e, f, stack);
        if(ModConfig.INSTANCE.BETTER_ITEM_POSITION){
            itemEntity.setVelocity(
                    side.getOffsetX() * ModConfig.INSTANCE.DISPENSE_DISTANCE,
                    0.1 * ModConfig.INSTANCE.DISPENSE_SPEED,
                    side.getOffsetZ() * ModConfig.INSTANCE.DISPENSE_DISTANCE
            );
        }else{
            double g = world.random.nextDouble() * 0.1 + 0.2;
            itemEntity.setVelocity(
                    world.random.nextTriangular((double)side.getOffsetX() * g, 0.0172275 * (double)speed),
                    world.random.nextTriangular(0.2, 0.0172275 * (double)speed),
                    world.random.nextTriangular((double)side.getOffsetZ() * g, 0.0172275 * (double)speed));


        }
        world.spawnEntity(itemEntity);
        info.cancel();
    }

}
