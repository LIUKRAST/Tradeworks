package net.liukrast.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.liukrast.TradeworksConstants;

public class TradeworksPartialModels {
    private TradeworksPartialModels() {}

    public static final PartialModel SHELF_PRICE_TAG = block("shelf/price_tag");

    private static PartialModel block(String path) {
        return PartialModel.of(TradeworksConstants.id("block/" + path));
    }

    public static void init() {}
}
