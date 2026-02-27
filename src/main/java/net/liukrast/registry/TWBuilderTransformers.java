package net.liukrast.registry;

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
import net.liukrast.block.ShelfBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class TWBuilderTransformers {

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> shelf(String name, NonNullSupplier<? extends Block> initialProps) {
        return b -> {
            TagKey<Block> soundTag = BlockTags.INSIDE_STEP_SOUND_BLOCKS;

            ItemBuilder<TableClothBlockItem, BlockBuilder<B, P>> item = b.initialProperties(initialProps)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(state -> {
                                var top = state.getValue(ShelfBlock.TOP);
                                var axis = state.getValue(ShelfBlock.HORIZONTAL_AXIS);
                                return new ConfiguredModel[]{ConfiguredModel.builder().modelFile(p.models().withExistingParent(name + "_shelf", p.modLoc("block/shelf/" + (top ? "top" : "bottom")))
                                        .texture("0", p.modLoc("block/shelf/" + name))).rotationY(axis == Direction.Axis.X ? 0 : 90).buildLast()};
                            }, TableClothBlock.HAS_BE))
                    .onRegister(CreateRegistrate.blockModel(() -> TableClothModel::new))
                    .tag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, soundTag)
                    .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.table_cloth"))
                    .item(TableClothBlockItem::new);

            return item.model((c, p) -> p.withExistingParent(name + "_shelf", p.modLoc("block/shelf/top"))
                            .texture("0", p.modLoc("block/shelf/" + name)))
                    .tag(AllTags.AllItemTags.TABLE_CLOTHS.tag)
                    .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
                            .requires(c.get())
                            .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                            .save(p, Create.asResource("crafting/logistics/" + c.getName() + "_clear")))
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
                            .renderType(RenderType.cutoutMipped().name)
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
                            .save(p, Create.asResource("crafting/logistics/" + c.getName() + "_clear")))
                    .build();
        };
    }
}
