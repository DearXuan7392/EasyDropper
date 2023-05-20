package com.dearxuan.easydropper.mixin;


import com.dearxuan.easydropper.Config.ModConfig;
import com.dearxuan.easydropper.Interface.IAutoDropperBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DropperBlockEntity.class)
public abstract class EasyDropperMixin extends DispenserBlockEntity implements IAutoDropperBlockEntity {

    public int dispenseCooldown = -1;

    public EasyDropperMixin(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    public void serverTick(World world, BlockPos pos, BlockState state, DropperBlockEntity blockEntity) {
        if(ModConfig.INSTANCE.DROPPER_AUTO_DISPENSE){
            --dispenseCooldown;
            if(dispenseCooldown <= 0 && !blockEntity.isEmpty()){
                IEasyDropperBlock dropperBlock = (IEasyDropperBlock) state.getBlock();
                dropperBlock.Invoke_dispense((ServerWorld) world, pos);
                dispenseCooldown = ModConfig.INSTANCE.DISPENSE_COOLDOWN;
            }
        }

    }
}
