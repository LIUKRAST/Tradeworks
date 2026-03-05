package net.liukrast.tradeworks.compat;

import com.google.common.collect.ImmutableMap;
import net.liukrast.tradeworks.compat.woodgood.WoodGoodCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.LoadingModList;

import java.util.Map;
import java.util.function.Supplier;

public class CompatManager {

    private CompatManager() {}

    private static final Map<String, String> COMPATS;

    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("everycomp", "it.tuo.package.compat.WoodGoodCompat");
        COMPATS = builder.build();
    }

    public static void init(IEventBus eventBus, ModContainer container) {
        COMPATS.forEach((modId, className) -> {
            if (LoadingModList.get().getModFileById(modId) == null) return;
            try {
                Class<?> clazz = Class.forName(className);
                Compat compat = (Compat) clazz.getDeclaredConstructor().newInstance();
                compat.init(eventBus, container);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load compat for " + modId, e);
            }

        });
    }
}
