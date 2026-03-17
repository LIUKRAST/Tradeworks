package net.liukrast.tradeworks.compat;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockItem;
import net.liukrast.tradeworks.TradeworksConstants;
import net.liukrast.tradeworks.block.MetalShelfBlock;
import net.liukrast.tradeworks.block.ShelfBlock;
import net.liukrast.tradeworks.block.SideShelfBlock;
import net.liukrast.tradeworks.registry.TradeworksBlocks;
import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;
import net.mehvahdjukaar.every_compat.api.PaletteStrategies;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;

import static net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodChildKeys.*;

public class EveryCompModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, ShelfBlock> shelves;
    public final SimpleEntrySet<WoodType, MetalShelfBlock> metal_shelves;
    public final SimpleEntrySet<WoodType, SideShelfBlock> side_shelves;

    public EveryCompModule() {
        super(TradeworksConstants.MOD_ID, "tw", TradeworksConstants.MOD_ID);
        EveryCompatAPI.registerModule(this);


        shelves = SimpleEntrySet.builder(WoodType.class, "shelf",
                TradeworksBlocks.SHELVES.get(0), () -> VanillaWoodTypes.OAK,
                w -> new ShelfBlock(Utils.copyPropertySafe(w.planks), w.id.getPath())
        )
                .addTexture(modRes("block/shelf/oak"), PaletteStrategies.PLANKS_STANDARD)
                .addTile(getModTile("shelf"))
                .setTabKey(modRes("base"))
                .copyParentDrop()
                .requiresChildren(PLANKS)
                .addRecipe(TradeworksConstants.id("crafting/logistics/oak_shelf"))
                .addCustomItem((a, b, c) -> new TableClothBlockItem(b, c))
                .addTag(BlockTags.INSIDE_STEP_SOUND_BLOCKS, Registries.BLOCK)
                .addTag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, Registries.BLOCK)
                .addTag(AllTags.AllItemTags.TABLE_CLOTHS.tag, Registries.ITEM)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .build();

        metal_shelves = SimpleEntrySet.builder(WoodType.class, "metal_shelf",
                        TradeworksBlocks.METAL_SHELVES.get(0), () -> VanillaWoodTypes.OAK,
                        w -> new MetalShelfBlock(Utils.copyPropertySafe(w.planks), w.id.getPath())
                )
                .addTexture(modRes("block/shelf/oak"), PaletteStrategies.PLANKS_STANDARD)
                .addTile(getModTile("shelf"))
                .setTabKey(modRes("base"))
                .copyParentDrop()
                .requiresChildren(SLAB)
                .addRecipe(TradeworksConstants.id("crafting/logistics/oak_metal_shelf"))
                .addCustomItem((a, b, c) -> new TableClothBlockItem(b, c))
                .addTag(BlockTags.INSIDE_STEP_SOUND_BLOCKS, Registries.BLOCK)
                .addTag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, Registries.BLOCK)
                .addTag(AllTags.AllItemTags.TABLE_CLOTHS.tag, Registries.ITEM)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .build();

        side_shelves = SimpleEntrySet.builder(WoodType.class, "side_shelf",
                        TradeworksBlocks.SIDE_SHELVES.get(0), () -> VanillaWoodTypes.OAK,
                        w -> new SideShelfBlock(Utils.copyPropertySafe(w.planks), w.id.getPath())
                )
                .requiresChildren(STAIRS)
                .addTile(getModTile("shelf"))
                .addTexture(modRes("block/shelf/oak"), PaletteStrategies.PLANKS_STANDARD)
                .addTag(BlockTags.INSIDE_STEP_SOUND_BLOCKS, Registries.BLOCK)
                .addTag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, Registries.BLOCK)
                .addTag(AllTags.AllItemTags.TABLE_CLOTHS.tag, Registries.ITEM)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTabKey(modRes("base"))
                .copyParentDrop()
                .addRecipe(TradeworksConstants.id("crafting/logistics/oak_side_shelf"))
                .addCustomItem((a, b, c) -> new TableClothBlockItem(b, c))
                .build();

        this.addEntry(shelves);
        this.addEntry(metal_shelves);
        this.addEntry(side_shelves);
    }
}
