package net.liukrast.tradeworks.block;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SideShelfBlockEntity extends TableClothBlockEntity {
    public SideShelfBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if(state.getBlock() instanceof SideShelfBlock)
            facing = state.getValue(SideShelfBlock.HORIZONTAL_FACING);
    }

    @Override
    public ItemInteractionResult use(Player player, BlockHitResult ray) {
        var res = super.use(player, ray);
        var state = getBlockState();
        if(state.getBlock() instanceof SideShelfBlock)
            facing = state.getValue(SideShelfBlock.HORIZONTAL_FACING);
        return res;
    }
}
