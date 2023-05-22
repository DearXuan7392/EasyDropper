package com.dearxuan.easydropper.Interface;


import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IAutoDropperBlockEntity {

    void serverTick(World world, BlockPos pos, BlockState state, DropperBlockEntity blockEntity);

    void SetEnable(boolean flag);
}
