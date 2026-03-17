package net.liukrast.tradeworks.registry;

import net.liukrast.tradeworks.TradeworksConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus;

public class TradeworksCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TradeworksConstants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tradeworks"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(TradeworksBlocks.SHELVES.get(0)::asStack)
                    .displayItems((pars, out) -> {
                        TradeworksBlocks.INVERTED_TABLE_CLOTHS.forEach(out::accept);
                        TradeworksBlocks.SHELVES.forEach(out::accept);
                        out.accept(TradeworksBlocks.ANDESITE_SHELF);
                        out.accept(TradeworksBlocks.BRASS_SHELF);
                        out.accept(TradeworksBlocks.COPPER_SHELF);
                        TradeworksBlocks.METAL_SHELVES.forEach(out::accept);
                        out.accept(TradeworksBlocks.ANDESITE_METAL_SHELF);
                        out.accept(TradeworksBlocks.BRASS_METAL_SHELF);
                        out.accept(TradeworksBlocks.COPPER_METAL_SHELF);
                        TradeworksBlocks.SIDE_SHELVES.forEach(out::accept);
                        out.accept(TradeworksBlocks.ANDESITE_SIDE_SHELF);
                        out.accept(TradeworksBlocks.BRASS_SIDE_SHELF);
                        out.accept(TradeworksBlocks.COPPER_SIDE_SHELF);
                    })
                    .build());

    @ApiStatus.Internal
    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
