package com.dearxuan.easydropper.mixin;


import com.dearxuan.easydropper.Interface.IAutoDropperBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DropperBlock.class)
public abstract class EasyDropperBlockMixin implements BlockEntityProvider {

    private int dispenseCooldown = -1;

    public EasyDropperBlockMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super();
    }

    @Nullable
    @Override
    @Shadow
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world,
            BlockState state,
            BlockEntityType<T> type) {
        if(world.isClient() || type != BlockEntityType.DROPPER){
            return null;
        }
        return EasyDropperBlockMixin::serverTick;
    }

    private static <T extends BlockEntity> void serverTick(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            T t) {
        DropperBlockEntity dropperBlockEntity = (DropperBlockEntity) t;
        ((IAutoDropperBlockEntity)dropperBlockEntity).serverTick(world, blockPos, blockState, dropperBlockEntity);
    }


}
