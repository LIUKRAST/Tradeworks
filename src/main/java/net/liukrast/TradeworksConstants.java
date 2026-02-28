package net.liukrast;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Stream;

public class TradeworksConstants {
    private TradeworksConstants() {}

    public static final String MOD_ID = "tradeworks";

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ResourceLocation id(String path, Object... args) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, String.format(path, args));
    }

    public static <T> Stream<T> getElements(Registry<T> registry) {
        return getElementEntries(registry).map(Map.Entry::getValue);
    }

    public static <T> Stream<Map.Entry<String, T>> getElementEntries(Registry<T> registry) {
        return registry.entrySet().stream()
                .filter(t -> t.getKey().location().getNamespace().equals(MOD_ID))
                .map(e -> Map.entry(e.getKey().location().getPath(), e.getValue()));
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
}
