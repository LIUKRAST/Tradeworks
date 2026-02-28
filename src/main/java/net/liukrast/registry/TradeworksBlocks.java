package net.liukrast.registry;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.liukrast.TradeworksConstants;
import net.liukrast.block.MetalShelfBlock;
import net.liukrast.block.ShelfBlock;
import net.liukrast.block.TableClothBlockImpl;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.List;

public class TradeworksBlocks {
    private static final CreateRegistrate REGISTRATE = TradeworksConstants.registrate();

    static {
        REGISTRATE.setCreativeTab(TradeworksCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final List<BlockEntry<ShelfBlock>> SHELVES = WoodType.values()
            .map(type -> REGISTRATE.block(type.name() + "_shelf", p -> new ShelfBlock(p, type.name()))
                    .transform(TWBuilderTransformers.shelf(type.name(), () -> Blocks.OAK_PLANKS))
                    //TODO: .properties(p -> p.mapColor())
                    /*.recipe((c, p) -> {
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
                    })*/
                    .register()
            ).toList();

    public static final List<BlockEntry<MetalShelfBlock>> METAL_SHELVES = WoodType.values()
            .map(type -> REGISTRATE.block(type.name() + "_metal_shelf", p -> new MetalShelfBlock(p, type.name()))
                    .transform(TWBuilderTransformers.shelf(type.name(), () -> Blocks.OAK_PLANKS, "metal_shelf", "cutout"))
                    //TODO: .properties(p -> p.mapColor())
                    /*.recipe((c, p) -> {
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
                    })*/
                    .register()
            ).toList();

    public static final DyedBlockList<TableClothBlock> INVERTED_TABLE_CLOTHS = new DyedBlockList<>(colour -> {
        String colourName = colour.getSerializedName();
        return REGISTRATE.block(colourName + "_inverted_table_cloth", p -> new TableClothBlockImpl(p, colour))
                .transform(TWBuilderTransformers.tableCloth(colourName, () -> Blocks.BLACK_CARPET, true, "inverted_table_cloth"))
                .properties(p -> p.mapColor(colour))
                /*.recipe((c, p) -> {
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
                })*/
                .register();
    });

    public static void register() {}
}
