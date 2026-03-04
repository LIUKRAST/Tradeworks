package net.liukrast;

import com.mojang.math.Axis;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public interface TableClothPlacement {
    int getPlacementId();

    Vec3 getPriceOffset(TableClothBlockEntity blockEntity, BlockState blockState);
    Vec3 getItemsOffset(TableClothBlockEntity blockEntity, BlockState blockState);

    Axis getRotatingItemsAxis();

    default boolean shouldRotate(TableClothBlockEntity be, BlockState state) {
        return false;
    }
}
