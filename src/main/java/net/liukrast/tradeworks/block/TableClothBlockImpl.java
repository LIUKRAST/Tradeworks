package net.liukrast.tradeworks.block;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.liukrast.tradeworks.registry.TradeworksBlockEntityTypes;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TableClothBlockImpl extends TableClothBlock {
    public TableClothBlockImpl(Properties pProperties, DyeColor colour) {
        super(pProperties, colour);
    }

    public TableClothBlockImpl(Properties pProperties, String type) {
        super(pProperties, type);
    }

    @Override
    public BlockEntityType<? extends TableClothBlockEntity> getBlockEntityType() {
        return TradeworksBlockEntityTypes.TABLE_CLOTH.get();
    }
}
