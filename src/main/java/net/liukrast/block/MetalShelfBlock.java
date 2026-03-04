package net.liukrast.block;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class MetalShelfBlock extends ShelfBlock {
    public MetalShelfBlock(Properties pProperties, String type) {
        super(pProperties, type);
    }

    @Override
    public Vec3 getPriceOffset(TableClothBlockEntity be, BlockState state) {
        return VecHelper.voxelSpace(0,-6,-1);
    }
}
