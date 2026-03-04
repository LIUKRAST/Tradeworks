package net.liukrast.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import net.liukrast.TableClothPlacement;
import net.liukrast.block.ShelfBlock;
import net.liukrast.block.SideShelfBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TableClothBlock.class)
public class TableClothBlockMixin {
    @ModifyExpressionValue(method = "useItemOn", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/logistics/tableCloth/TableClothBlock;placementHelperId:I", opcode = Opcodes.GETSTATIC))
    private int useItemOn(int original) {
        if(this instanceof TableClothPlacement tcp) return tcp.getPlacementId();
        return original;
    }

    @Mixin(targets = "com.simibubi.create.content.logistics.tableCloth.TableClothBlock$PlacementHelper")
    public static class PlacementHelperMixin {
        @ModifyReturnValue(
                method = "lambda$getItemPredicate$0",
                at = @At(value = "RETURN")
        )
        private static boolean lambda$getItemPredicate$0(boolean original, @Local(argsOnly = true) ItemStack stack) {
            return original && !ShelfBlock.isShelf(stack) && !SideShelfBlock.isShelf(stack);
        }

        @ModifyReturnValue(
                method = "lambda$getStatePredicate$1",
                at = @At("RETURN")
        )
        private static boolean lambda$getStatePredicate$1(boolean original, @Local(argsOnly = true) BlockState state) {
            return original && !(state.getBlock() instanceof TableClothPlacement);
        }
    }
}
