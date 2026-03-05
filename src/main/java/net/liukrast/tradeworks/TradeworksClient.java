package net.liukrast.tradeworks;

import net.liukrast.tradeworks.registry.TradeworksPartialModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = TradeworksConstants.MOD_ID, dist = Dist.CLIENT)
public class TradeworksClient {
    public TradeworksClient(IEventBus ignored, ModContainer ignored1) {
        TradeworksPartialModels.init();
    }
}
