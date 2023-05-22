package com.dearxuan.easydropper.mixin;


import com.dearxuan.easydropper.Config.ModConfig;
import com.dearxuan.easydropper.Interface.IAutoDropperBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DropperBlock.class)
public abstract class EasyDropperBlock extends DispenserBlock implements BlockEntityProvider {

    public EasyDropperBlock(Settings settings) {
        super(settings);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world,
            BlockState state,
            BlockEntityType<T> type) {
        if(world.isClient() || type != BlockEntityType.DROPPER){
            return null;
        }
        return EasyDropperBlock::serverTick;
    }

    private static <T extends BlockEntity> void serverTick(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            T t) {
        DropperBlockEntity dropperBlockEntity = (DropperBlockEntity) t;
        ((IAutoDropperBlockEntity)dropperBlockEntity).serverTick(world, blockPos, blockState, dropperBlockEntity);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(!ModConfig.INSTANCE.DROPPER_AUTO_DISPENSE){
            super.scheduledTick(state, world, pos, random);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if(ModConfig.INSTANCE.DROPPER_AUTO_DISPENSE){
            ((IAutoDropperBlockEntity)world.getBlockEntity(pos)).SetEnable(!world.isReceivingRedstonePower(pos));
        }else{
            super.onBlockAdded(state, world, pos, oldState, notify);
        }
    }


    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(ModConfig.INSTANCE.DROPPER_AUTO_DISPENSE){
            ((IAutoDropperBlockEntity)world.getBlockEntity(pos)).SetEnable(!world.isReceivingRedstonePower(pos));
        }else{
            super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        }

    }
}
