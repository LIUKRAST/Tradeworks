package net.liukrast.tradeworks.registry;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockItem;
import com.simibubi.create.content.logistics.tableCloth.TableClothModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.liukrast.tradeworks.TradeworksConstants;
import net.liukrast.tradeworks.block.ShelfBlock;
import net.liukrast.tradeworks.block.SideShelfBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.ApiStatus;

public class TWBuilderTransformers {

    @ApiStatus.Internal
    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> shelf(String name, NonNullSupplier<? extends Block> initialProps) {
        return shelf(name, initialProps, "shelf", "solid");
    }

    @ApiStatus.Internal
    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> shelf(String name, NonNullSupplier<? extends Block> initialProps, String path, String renderType) {
        return b -> {
            ItemBuilder<TableClothBlockItem, BlockBuilder<B, P>> item = b.initialProperties(initialProps)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(state -> {
                                var top = state.getValue(ShelfBlock.TOP);
                                var axis = state.getValue(ShelfBlock.HORIZONTAL_AXIS);
                                return new ConfiguredModel[]{ConfiguredModel.builder().modelFile(p.models().withExistingParent("block/" + path + "/" + name + (top ? "_top" : "_bottom"), p.modLoc("block/" + path + "/" + (top ? "top" : "bottom")))
                                        .texture("0", p.modLoc("block/shelf/" + name)).renderType(renderType).texture("particle", p.modLoc("block/shelf/" + name))).rotationY(axis == Direction.Axis.Z ? 0 : 90).buildLast()};
                            }, TableClothBlock.HAS_BE))
                    //.onRegister(CreateRegistrate.blockModel(() -> TableClothModel::new))
                    .tag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, BlockTags.INSIDE_STEP_SOUND_BLOCKS)
                    .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.table_cloth"))
                    .item(TableClothBlockItem::new);

            return item.model((c, p) -> p.withExistingParent(name + "_" + path, p.modLoc("block/" + path + "/top"))
                            .texture("0", p.modLoc("block/shelf/" + name)))
                    .tag(AllTags.AllItemTags.TABLE_CLOTHS.tag)
                    .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
                            .requires(c.get())
                            .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                            .save(p, Create.asResource("crafting/logistics/" + c.getName() + "_clear")))
                    .build();
        };
    }

    @ApiStatus.Internal
    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> sideShelf(String name, NonNullSupplier<? extends Block> initialProps, String path, String renderType) {
        return b -> {
            ItemBuilder<TableClothBlockItem, BlockBuilder<B, P>> item = b.initialProperties(initialProps)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(state -> {
                                var face = state.getValue(SideShelfBlock.HORIZONTAL_FACING);
                                return new ConfiguredModel[]{ConfiguredModel.builder().modelFile(p.models().withExistingParent("block/" + path + "/" + name, p.modLoc("block/" + path))
                                        .texture("0", p.modLoc("block/shelf/" + name)).renderType(renderType).texture("particle", p.modLoc("block/shelf/" + name)))
                                        .rotationY((int) ((face.toYRot() + 180)%360))
                                        .buildLast()};
                            }, TableClothBlock.HAS_BE))
                    //.onRegister(CreateRegistrate.blockModel(() -> TableClothModel::new))
                    .tag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, BlockTags.INSIDE_STEP_SOUND_BLOCKS)
                    .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.table_cloth"))
                    .item(TableClothBlockItem::new);

            return item.model((c, p) -> p.withExistingParent(name + "_" + path, p.modLoc("block/" + path))
                            .texture("0", p.modLoc("block/shelf/" + name)))
                    .tag(AllTags.AllItemTags.TABLE_CLOTHS.tag)
                    .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
                    .requires(c.get())
                    .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                    .save(p, TradeworksConstants.id("crafting/logistics/" + c.getName() + "_clear")))
                    .build();
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> tableCloth(String name,
                                                                                           NonNullSupplier<? extends Block> initialProps, boolean dyed, String path) {
        return b -> {
            TagKey<Block> soundTag = dyed ? BlockTags.COMBINATION_STEP_SOUND_BLOCKS : BlockTags.INSIDE_STEP_SOUND_BLOCKS;

            ItemBuilder<TableClothBlockItem, BlockBuilder<B, P>> item = b.initialProperties(initialProps)
                    .blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
                            .getBuilder(name + "_" + path)
                            .parent(new ModelFile.UncheckedModelFile(Create.asResource("block/table_cloth/block")))
                            .renderType("cutout_mipped")
                            .texture("0", p.modLoc("block/" + path + "/" + name))))
                    .onRegister(CreateRegistrate.blockModel(() -> TableClothModel::new))
                    .tag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, soundTag)
                    .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.table_cloth"))
                    .item(TableClothBlockItem::new);

            if (dyed)
                item.tag(AllTags.AllItemTags.DYED_TABLE_CLOTHS.tag);

            return item.model((c, p) -> p.getBuilder(name + "_" + path).parent(new ModelFile.UncheckedModelFile(Create.asResource("block/table_cloth/item")))
                            .texture("0", p.modLoc("block/" + path + "/" + name)))
                    .tag(AllTags.AllItemTags.TABLE_CLOTHS.tag)
                    .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
                            .requires(c.get())
                            .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                            .save(p, TradeworksConstants.id("crafting/logistics/" + c.getName() + "_clear")))
                    .build();
        };
    }
}
