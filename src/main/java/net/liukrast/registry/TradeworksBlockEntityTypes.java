package net.liukrast.registry;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import com.simibubi.create.content.logistics.tableCloth.TableClothRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.liukrast.TradeworksConstants;
import net.liukrast.block.ShelfBlock;
import net.liukrast.block.ShelfRenderer;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.List;

public class TradeworksBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = TradeworksConstants.registrate();

    public static final BlockEntityEntry<TableClothBlockEntity> TABLE_CLOTH =
            REGISTRATE.blockEntity("table_cloth", TableClothBlockEntity::new)
                    .validBlocks(TradeworksBlocks.INVERTED_TABLE_CLOTHS.toArray())
                    .renderer(() -> TableClothRenderer::new)
                    .register();

    public static final BlockEntityEntry<TableClothBlockEntity> SHELF =
            REGISTRATE.blockEntity("shelf", TableClothBlockEntity::new)
                    .validBlocks(toArray(TradeworksBlocks.SHELVES))
                    .validBlocks(toArray(TradeworksBlocks.METAL_SHELVES))
                    .renderer(() -> ShelfRenderer::new)
                    .register();

    @SuppressWarnings("unchecked")
    private static <T extends Block> BlockEntry<T>[] toArray(List<BlockEntry<T>> list) {
        return list.toArray((BlockEntry<T>[]) new BlockEntry[0]);
    }


    public static void register() {}
}
