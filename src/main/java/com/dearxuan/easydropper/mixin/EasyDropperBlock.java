package com.dearxuan.easydropper.mixin;


import com.dearxuan.easydropper.EasyDropper;
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
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DropperBlock.class)
public abstract class EasyDropperBlock extends AbstractBlock implements BlockEntityProvider {

    public EasyDropperBlock(Settings settings) {
        super(settings);
    }

    @Shadow
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
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
        // 什么事情都不做
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        ((IAutoDropperBlockEntity)world.getBlockEntity(pos)).SetEnable(!world.isReceivingRedstonePower(pos));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        ((IAutoDropperBlockEntity)world.getBlockEntity(pos)).SetEnable(!world.isReceivingRedstonePower(pos));
    }
}
