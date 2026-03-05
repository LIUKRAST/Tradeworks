package net.liukrast.tradeworks.block;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class MetalShelfBlock extends ShelfBlock {
    private static final VoxelShape TOP = box(0,0,0, 16, 8, 16);

    public MetalShelfBlock(Properties pProperties, String type) {
        super(pProperties, type);
    }

    @Override
    public Vec3 getPriceOffset(TableClothBlockEntity be, BlockState state) {
        return VecHelper.voxelSpace(0,-6,-1);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return state.getValue(ShelfBlock.TOP) ? TOP : Shapes.block();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }
}
