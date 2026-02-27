package net.liukrast;

import net.liukrast.registry.TradeworksBlockEntityTypes;
import net.liukrast.registry.TradeworksBlocks;
import net.liukrast.registry.TradeworksCreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(TradeworksConstants.MOD_ID)
public class Tradeworks {
    public Tradeworks(IEventBus eventBus, ModContainer modContainer) {
        TradeworksConstants.registrate().registerEventListeners(eventBus);
        TradeworksBlocks.register();
        TradeworksBlockEntityTypes.register();
        TradeworksCreativeModeTabs.register(eventBus);
    }

}
