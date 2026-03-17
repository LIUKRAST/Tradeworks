package net.liukrast.tradeworks.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.liukrast.tradeworks.TradeworksConstants;

public class TradeworksPartialModels {
    private TradeworksPartialModels() {}

    public static final PartialModel SHELF_PRICE_TAG = PartialModel.of(TradeworksConstants.id("block/" + "shelf/price_tag"));

    public static void init() {}
}
