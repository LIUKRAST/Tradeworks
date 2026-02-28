package net.liukrast.registry;

import net.liukrast.TradeworksConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

public class TradeworksCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TradeworksConstants.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tradeworks"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(TradeworksBlocks.SHELVES.getFirst()::asStack)
                    .displayItems((pars, out) -> {
                        TradeworksBlocks.INVERTED_TABLE_CLOTHS.forEach(out::accept);
                        TradeworksBlocks.SHELVES.forEach(out::accept);
                        TradeworksBlocks.METAL_SHELVES.forEach(out::accept);
                    })
                    .build());

    @ApiStatus.Internal
    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
