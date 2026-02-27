package net.liukrast.registry;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import com.simibubi.create.content.logistics.tableCloth.TableClothRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.liukrast.TradeworksConstants;

public class TradeworksBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = TradeworksConstants.registrate();

    public static final BlockEntityEntry<TableClothBlockEntity> TABLE_CLOTH =
            REGISTRATE.blockEntity("table_cloth", TableClothBlockEntity::new)
                    .validBlocks(TradeworksBlocks.INVERTED_TABLE_CLOTHS.toArray())
                    .renderer(() -> TableClothRenderer::new)
                    .register();

    public static void register() {}
}
