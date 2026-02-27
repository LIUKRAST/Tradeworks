package net.liukrast.registry;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.DyeHelper;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.liukrast.TradeworksConstants;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;

public class TradeworksBlocks {
    private static final CreateRegistrate REGISTRATE = TradeworksConstants.registrate();

    static {
        REGISTRATE.setCreativeTab(TradeworksCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final DyedBlockList<TableClothBlock> INVERTED_TABLE_CLOTHS = new DyedBlockList<>(colour -> {
        String colourName = colour.getSerializedName();
        return REGISTRATE.block(colourName + "_table_cloth", p -> new TableClothBlock(p, colour))
                .transform(BuilderTransformers.tableCloth(colourName, () -> Blocks.BLACK_CARPET, true))
                .properties(p -> p.mapColor(colour))
                .recipe((c, p) -> {
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                            .requires(DyeHelper.getWoolOfDye(colour))
                            .requires(AllItems.ANDESITE_ALLOY)
                            .unlockedBy("has_wool", RegistrateRecipeProvider.has(ItemTags.WOOL))
                            .save(p, Create.asResource("crafting/logistics/" + c.getName()));
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
                            .requires(colour.getTag())
                            .requires(AllTags.AllItemTags.DYED_TABLE_CLOTHS.tag)
                            .unlockedBy("has_postbox", RegistrateRecipeProvider.has(AllTags.AllItemTags.DYED_TABLE_CLOTHS.tag))
                            .save(p, Create.asResource("crafting/logistics/" + c.getName() + "_from_other_table_cloth"));
                })
                .register();
    });

    public static void register() {}
}
