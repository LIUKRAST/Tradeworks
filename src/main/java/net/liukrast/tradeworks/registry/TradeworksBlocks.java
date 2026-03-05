package net.liukrast.tradeworks.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.liukrast.tradeworks.TradeworksConstants;
import net.liukrast.tradeworks.block.MetalShelfBlock;
import net.liukrast.tradeworks.block.ShelfBlock;
import net.liukrast.tradeworks.block.SideShelfBlock;
import net.liukrast.tradeworks.block.TableClothBlockImpl;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Arrays;
import java.util.List;

public class TradeworksBlocks {
    private static final CreateRegistrate REGISTRATE = TradeworksConstants.registrate();

    // Avoid using custom wood types. We have wood-good compatibility for that!!
    private static final WoodType[] WOOD_TYPES = {
            WoodType.OAK, WoodType.SPRUCE, WoodType.BIRCH, WoodType.ACACIA, WoodType.CHERRY, WoodType.JUNGLE, WoodType.DARK_OAK, WoodType.CRIMSON, WoodType.WARPED, WoodType.MANGROVE, WoodType.BAMBOO
    };

    static {
        REGISTRATE.setCreativeTab(TradeworksCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final List<BlockEntry<ShelfBlock>> SHELVES = Arrays.stream(WOOD_TYPES)
            .map(type -> REGISTRATE.block(type.name() + "_shelf", p -> new ShelfBlock(p, type.name()))
                    .transform(TWBuilderTransformers.shelf(type.name(), () -> Blocks.OAK_PLANKS))
                    .tag(BlockTags.MINEABLE_WITH_AXE)
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                            .pattern("AAA")
                            .pattern("ABA")
                            .define('A', BuiltInRegistries.ITEM.get(ResourceLocation.parse(type.name() + "_slab")))
                            .define('B', AllItems.ANDESITE_ALLOY)
                            .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                            .save(p, TradeworksConstants.id("crafting/logistics/" + c.getName())))
                    .register()
            ).toList();

    public static final BlockEntry<ShelfBlock> ANDESITE_SHELF = createShelf("andesite", AllBlocks.ANDESITE_CASING);
    public static final BlockEntry<ShelfBlock> BRASS_SHELF = createShelf("brass", AllBlocks.BRASS_CASING);
    public static final BlockEntry<ShelfBlock> COPPER_SHELF = createShelf("copper", AllBlocks.COPPER_CASING);

    private static BlockEntry<ShelfBlock> createShelf(String prefix, BlockEntry<? extends Block> sb) {
        return REGISTRATE.block(prefix + "_shelf", p -> new ShelfBlock(p, prefix))
                .transform(TWBuilderTransformers.shelf(prefix, sb, "shelf", "cutout"))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> SingleItemRecipeBuilder.stonecutting(Ingredient.of(sb), RecipeCategory.MISC, c.get())
                        .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                        .save(p, TradeworksConstants.id("stone_cutting/logistics/" + c.getName())))
                .register();
    }

    public static final List<BlockEntry<MetalShelfBlock>> METAL_SHELVES = Arrays.stream(WOOD_TYPES)
            .map(type -> REGISTRATE.block(type.name() + "_metal_shelf", p -> new MetalShelfBlock(p, type.name()))
                    .transform(TWBuilderTransformers.shelf(type.name(), () -> Blocks.OAK_PLANKS, "metal_shelf", "cutout"))
                    .tag(BlockTags.MINEABLE_WITH_AXE)
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 2)
                            .pattern(" C ")
                            .pattern("ABA")
                            .pattern("ABA")
                            .define('A', Items.CHAIN)
                            .define('B', BuiltInRegistries.ITEM.get(ResourceLocation.parse(type.name() + "_slab")))
                            .define('C', AllItems.ANDESITE_ALLOY)
                            .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                            .save(p, TradeworksConstants.id("crafting/logistics/" + c.getName()))
                    )
                    .register()
            ).toList();

    public static final BlockEntry<MetalShelfBlock> ANDESITE_METAL_SHELF = createMetalShelf("andesite", AllBlocks.ANDESITE_CASING);
    public static final BlockEntry<MetalShelfBlock> BRASS_METAL_SHELF = createMetalShelf("brass", AllBlocks.BRASS_CASING);
    public static final BlockEntry<MetalShelfBlock> COPPER_METAL_SHELF = createMetalShelf("copper", AllBlocks.COPPER_CASING);

    private static BlockEntry<MetalShelfBlock> createMetalShelf(String prefix, BlockEntry<? extends Block> sb) {
        return REGISTRATE.block(prefix + "_metal_shelf", p -> new MetalShelfBlock(p, prefix))
                .transform(TWBuilderTransformers.shelf(prefix, sb, "metal_shelf", "cutout"))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> SingleItemRecipeBuilder.stonecutting(Ingredient.of(sb), RecipeCategory.MISC, c.get())
                        .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                        .save(p, TradeworksConstants.id("stone_cutting/logistics/" + c.getName())))
                .register();
    }

    public static final List<BlockEntry<SideShelfBlock>> SIDE_SHELVES = Arrays.stream(WOOD_TYPES)
            .map(type -> REGISTRATE.block(type.name() + "_side_shelf", p -> new SideShelfBlock(p, type.name()))
                    .transform(TWBuilderTransformers.sideShelf(type.name(), () -> Blocks.OAK_PLANKS, "side_shelf", "cutout"))
                    .tag(BlockTags.MINEABLE_WITH_AXE)
                    .recipe((c, p) -> ShapedRecipeBuilder
                            .shaped(RecipeCategory.MISC, c.get(), 2)
                            .pattern(" C ")
                            .pattern("ABA")
                            .pattern("ABA")
                            .define('A', Items.CHAIN)
                            .define('B', BuiltInRegistries.ITEM.get(ResourceLocation.parse(type.name() + "_stairs")))
                            .define('C', AllItems.ANDESITE_ALLOY)
                            .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                            .save(p, TradeworksConstants.id("crafting/logistics/" + c.getName()))
                    )
                    .register()
            ).toList();

    public static final BlockEntry<SideShelfBlock> ANDESITE_SIDE_SHELF = createSideShelf("andesite", AllBlocks.ANDESITE_CASING);
    public static final BlockEntry<SideShelfBlock> BRASS_SIDE_SHELF = createSideShelf("brass", AllBlocks.BRASS_CASING);
    public static final BlockEntry<SideShelfBlock> COPPER_SIDE_SHELF = createSideShelf("copper", AllBlocks.COPPER_CASING);

    private static BlockEntry<SideShelfBlock> createSideShelf(String prefix, BlockEntry<? extends Block> sb) {
        return REGISTRATE.block(prefix + "_side_shelf", p -> new SideShelfBlock(p, prefix))
                .transform(TWBuilderTransformers.sideShelf(prefix, sb, "side_shelf", "cutout"))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> SingleItemRecipeBuilder.stonecutting(Ingredient.of(sb), RecipeCategory.MISC, c.get(), 2)
                        .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                        .save(p, TradeworksConstants.id("stone_cutting/logistics/" + c.getName())))
                .register();
    }

    public static final DyedBlockList<TableClothBlock> INVERTED_TABLE_CLOTHS = new DyedBlockList<>(colour -> {
        String colourName = colour.getSerializedName();
        return REGISTRATE.block(colourName + "_inverted_table_cloth", p -> new TableClothBlockImpl(p, colour))
                .transform(TWBuilderTransformers.tableCloth(colourName, () -> Blocks.BLACK_CARPET, true, "inverted_table_cloth"))
                .properties(p -> p.mapColor(colour))
                .tag(BlockTags.MINEABLE_WITH_AXE)
                .recipe((c, p) -> {
                    var opp = AllBlocks.TABLE_CLOTHS.get(colour);
                    SingleItemRecipeBuilder.stonecutting(
                            Ingredient.of(opp),
                            RecipeCategory.BUILDING_BLOCKS,
                            c.get()
                    )
                            .unlockedBy("has_wool", RegistrateRecipeProvider.has(ItemTags.WOOL))
                            .save(p, TradeworksConstants.id("crafting/logistics/" + c.getName()));
                    SingleItemRecipeBuilder.stonecutting(
                                    Ingredient.of(c.get()),
                                    RecipeCategory.BUILDING_BLOCKS,
                                    opp
                            )
                            .unlockedBy("has_wool", RegistrateRecipeProvider.has(ItemTags.WOOL))
                            .save(p, TradeworksConstants.id("crafting/logistics/" + opp.getId().getPath()));
                })
                .register();
    });

    public static void register() {}
}
