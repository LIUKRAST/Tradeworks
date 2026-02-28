package net.liukrast.block;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class MetalShelfBlock extends ShelfBlock {
    public MetalShelfBlock(Properties pProperties, String type) {
        super(pProperties, type);
    }

    @Override
    public Vec3 getValueBox(TableClothBlockEntity be, BlockState state) {
        return super.getValueBox(be, state);
    }

    @Override
    public boolean shouldRenderSide(TableClothBlockEntity be) {
        return false;
    }
}
