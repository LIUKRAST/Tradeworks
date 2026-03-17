package net.liukrast.tradeworks.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.liukrast.tradeworks.TradeworksConstants;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.function.BiConsumer;

public class TradeworksDatagen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        addExtraRegistrateData();
    }

    private static void addExtraRegistrateData() {
        TradeworksConstants.registrate().addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            provideDefaultLang("interface", langConsumer);
            provideDefaultLang("tooltips", langConsumer);
        });
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/tradeworks/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
}
