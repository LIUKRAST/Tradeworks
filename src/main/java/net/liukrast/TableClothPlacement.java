package net.liukrast;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public interface TableClothPlacement {
    int getPlacementId();

    Vec3 getOffset(TableClothBlockEntity blockEntity, BlockState blockState);

    default boolean shouldRotate(TableClothBlockEntity be, BlockState state) {
        return false;
    }
}
