package net.liukrast.tradeworks.compat;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class Compat {
    public void init(IEventBus eventBus, ModContainer container) {
        eventBus.register(this);
    }
}