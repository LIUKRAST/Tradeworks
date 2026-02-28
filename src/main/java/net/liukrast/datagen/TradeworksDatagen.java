package net.liukrast.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.AllKeys;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.simibubi.create.infrastructure.data.TagLangGenerator;
import com.tterrag.registrate.providers.ProviderType;
import net.liukrast.TradeworksConstants;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.function.BiConsumer;

public class TradeworksDatagen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(TradeworksConstants.MOD_ID))
            addExtraRegistrateData();
    }

    private static void addExtraRegistrateData() {
        TradeworksConstants.registrate().addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            provideDefaultLang("interface", langConsumer);
            provideDefaultLang("tooltips", langConsumer);
            new TagLangGenerator(langConsumer).generate();
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
