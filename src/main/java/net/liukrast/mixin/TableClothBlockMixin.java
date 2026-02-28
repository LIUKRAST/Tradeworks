package net.liukrast.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.liukrast.TradeworksConstants;
import net.liukrast.block.ShelfBlock;
import net.liukrast.registry.TradeworksBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "com.simibubi.create.content.logistics.tableCloth.TableClothBlock$PlacementHelper")
public class TableClothBlockMixin {
    @ModifyReturnValue(
            method = "lambda$getItemPredicate$0",
            at = @At(value = "RETURN")
    )
    private static boolean lambda$getItemPredicate$0(boolean original, @Local(argsOnly = true) ItemStack stack) {
        return original && !ShelfBlock.isShelf(stack);
    }

    @ModifyReturnValue(
            method = "lambda$getStatePredicate$1",
            at = @At("RETURN")
    )
    private static boolean lambda$getStatePredicate$1(boolean original, @Local(argsOnly = true)BlockState state) {
        return original && !(state.getBlock() instanceof ShelfBlock);
    }

}
