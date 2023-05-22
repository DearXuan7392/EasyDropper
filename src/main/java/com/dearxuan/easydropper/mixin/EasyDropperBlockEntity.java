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
public abstract class EasyDropperBlockEntity extends DispenserBlockEntity implements IAutoDropperBlockEntity {

    public int dispenseCooldown = -1;

    private int _Enable = 0;

    public EasyDropperBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    public void SetEnable(boolean flag){
        if(flag){
            _Enable = 1;
        }else{
            _Enable = -1;
        }
    }

    private boolean GetEnable(){
        if (_Enable == 1){
            return true;
        } else if (_Enable == -1){
            return false;
        } else {
            if(this.getWorld().isReceivingRedstonePower(this.getPos())){
                _Enable = -1;
                return false;
            }else{
                _Enable = 1;
                return true;
            }
        }
    }

    public void serverTick(World world, BlockPos pos, BlockState state, DropperBlockEntity blockEntity) {
        if(ModConfig.INSTANCE.DROPPER_AUTO_DISPENSE){
            --dispenseCooldown;
            if(!blockEntity.isEmpty() && dispenseCooldown <= 0 && GetEnable()){
                IEasyDropperBlock dropperBlock = (IEasyDropperBlock) state.getBlock();
                dropperBlock.Invoke_dispense((ServerWorld) world, pos);
                dispenseCooldown = ModConfig.INSTANCE.DROPPER_COOLDOWN;
            }
        }
    }
}
