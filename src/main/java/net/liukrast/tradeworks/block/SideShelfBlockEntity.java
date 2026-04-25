package net.liukrast.tradeworks.block;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SideShelfBlockEntity extends TableClothBlockEntity {
    public SideShelfBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        checkFacing();
    }

    public void checkFacing() {
        if(getBlockState().getBlock() instanceof SideShelfBlock) {
            var dir = getBlockState().getValue(SideShelfBlock.HORIZONTAL_FACING);
            if(dir != facing) {
                facing = dir;
                setChanged();
                sendData();
            }
        } else if(getBlockState().getBlock() instanceof ShelfBlock) {
            var axis = getBlockState().getValue(ShelfBlock.HORIZONTAL_AXIS);
            for(Direction dir : Direction.values()) {
                if(dir.getAxis() == axis) {
                    if(dir != facing) {
                        facing = dir;
                        setChanged();
                        sendData();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public ItemInteractionResult use(Player player, BlockHitResult ray) {
        var res = super.use(player, ray);
        checkFacing();
        return res;
    }
}
