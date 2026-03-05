package net.liukrast.tradeworks;

import net.liukrast.tradeworks.compat.CompatManager;
import net.liukrast.tradeworks.datagen.TradeworksDatagen;
import net.liukrast.tradeworks.registry.TradeworksBlockEntityTypes;
import net.liukrast.tradeworks.registry.TradeworksBlocks;
import net.liukrast.tradeworks.registry.TradeworksCreativeModeTabs;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(TradeworksConstants.MOD_ID)
public class Tradeworks {
    public Tradeworks(IEventBus eventBus, ModContainer modContainer) {
        CompatManager.init(eventBus, modContainer);
        TradeworksConstants.registrate().registerEventListeners(eventBus);
        TradeworksBlocks.register();
        TradeworksBlockEntityTypes.register();
        TradeworksCreativeModeTabs.register(eventBus);
        eventBus.addListener(EventPriority.HIGHEST, TradeworksDatagen::gatherDataHighPriority);
    }


}
