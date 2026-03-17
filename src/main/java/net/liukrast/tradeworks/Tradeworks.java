package net.liukrast.tradeworks;

import net.liukrast.tradeworks.datagen.TradeworksDatagen;
import net.liukrast.tradeworks.registry.TradeworksBlockEntityTypes;
import net.liukrast.tradeworks.registry.TradeworksBlocks;
import net.liukrast.tradeworks.registry.TradeworksCreativeModeTabs;
import net.liukrast.tradeworks.registry.TradeworksPartialModels;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.LoadingModList;

import java.lang.reflect.InvocationTargetException;

@Mod(TradeworksConstants.MOD_ID)
public class Tradeworks {
    public Tradeworks(FMLJavaModLoadingContext context) {
        var eventBus = context.getModEventBus();
        eventBus.register(this);
        TradeworksConstants.registrate().registerEventListeners(eventBus);
        TradeworksBlocks.register();
        TradeworksBlockEntityTypes.register();
        TradeworksCreativeModeTabs.register(eventBus);
        eventBus.addListener(EventPriority.LOWEST, TradeworksDatagen::gatherDataHighPriority);

        try {
            if (LoadingModList.get().getModFileById("everycomp") != null) {
                Class<?> clazz = Class.forName("net.liukrast.tradeworks.compat.EveryCompModule");
                clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            TradeworksConstants.LOGGER.error("Unable to load compat", e);
        }
    }

    @SubscribeEvent
    public void client(FMLClientSetupEvent event) {
        TradeworksPartialModels.init();
    }
}
