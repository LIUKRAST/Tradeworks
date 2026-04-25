package net.liukrast.tradeworks.registry;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import com.simibubi.create.content.logistics.tableCloth.TableClothRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.liukrast.tradeworks.TradeworksConstants;
import net.liukrast.tradeworks.block.ShelfRenderer;
import net.liukrast.tradeworks.block.SideShelfBlockEntity;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class TradeworksBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = TradeworksConstants.registrate();

    public static final BlockEntityEntry<TableClothBlockEntity> TABLE_CLOTH =
            REGISTRATE.blockEntity("table_cloth", TableClothBlockEntity::new)
                    .validBlocks(TradeworksBlocks.INVERTED_TABLE_CLOTHS.toArray())
                    .renderer(() -> TableClothRenderer::new)
                    .register();

    public static final BlockEntityEntry<SideShelfBlockEntity> SHELF =
            REGISTRATE.blockEntity("shelf", SideShelfBlockEntity::new)
                    .validBlocks(toArray(TradeworksBlocks.SHELVES))
                    .validBlocks(toArray(TradeworksBlocks.METAL_SHELVES))
                    .validBlocks(toArray(TradeworksBlocks.SIDE_SHELVES))
                    .validBlocks(TradeworksBlocks.ANDESITE_SHELF, TradeworksBlocks.ANDESITE_METAL_SHELF, TradeworksBlocks.ANDESITE_SIDE_SHELF)
                    .validBlocks(TradeworksBlocks.BRASS_SHELF, TradeworksBlocks.BRASS_METAL_SHELF, TradeworksBlocks.BRASS_SIDE_SHELF)
                    .validBlocks(TradeworksBlocks.COPPER_SHELF, TradeworksBlocks.COPPER_METAL_SHELF, TradeworksBlocks.COPPER_SIDE_SHELF)
                    .renderer(() -> ShelfRenderer::new)
                    .register();

    @SuppressWarnings("unchecked")
    private static <T extends Block> BlockEntry<T>[] toArray(List<BlockEntry<T>> list) {
        return list.toArray((BlockEntry<T>[]) new BlockEntry[0]);
    }


    public static void register() {}
}
