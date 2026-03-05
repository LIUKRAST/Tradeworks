package net.liukrast.tradeworks.compat;

import com.google.common.collect.ImmutableMap;
import net.liukrast.tradeworks.compat.woodgood.WoodGoodCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.LoadingModList;

import java.util.Map;

public class CompatManager {

    private CompatManager() {}

    private static final Map<String, Compat> COMPATS;

    static {
        ImmutableMap.Builder<String, Compat> builder = ImmutableMap.builder();
        builder.put("everycomp", new WoodGoodCompat());
        COMPATS = builder.build();
    }

    public static void init(IEventBus eventBus, ModContainer container) {
        COMPATS.forEach((modId, compat) -> {
            if(LoadingModList.get().getModFileById(modId) == null) return;
            compat.init(eventBus, container);
        });
    }
}
