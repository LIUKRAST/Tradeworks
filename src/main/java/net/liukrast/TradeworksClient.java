package net.liukrast;

import net.liukrast.registry.TradeworksPartialModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = TradeworksConstants.MOD_ID, dist = Dist.CLIENT)
public class TradeworksClient {
    public TradeworksClient(IEventBus eventBus, ModContainer container) {
        TradeworksPartialModels.init();
    }
}
