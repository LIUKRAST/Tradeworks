package net.liukrast.tradeworks.compat.woodgood;

import net.liukrast.tradeworks.compat.Compat;
import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class WoodGoodCompat extends Compat {

    @Override
    public void init(IEventBus eventBus, ModContainer container) {
        EveryCompatAPI.registerModule(new WoodGoodModule());
    }
}
