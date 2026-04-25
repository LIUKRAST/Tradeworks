package net.liukrast.tradeworks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.redstoneRequester.AutoRequestData;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.liukrast.tradeworks.TableClothPlacement;
import net.liukrast.tradeworks.block.ShelfBlock;
import net.liukrast.tradeworks.block.SideShelfBlock;
import net.liukrast.tradeworks.block.SideShelfBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TableClothBlock.class)
public class TableClothBlockMixin {
    @ModifyExpressionValue(method = "useItemOn", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/logistics/tableCloth/TableClothBlock;placementHelperId:I", opcode = Opcodes.GETSTATIC))
    private int useItemOn(int original) {
        if(this instanceof TableClothPlacement tcp) return tcp.getPlacementId();
        return original;
    }

    @Inject(
            method = "lambda$setPlacedBy$0",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/Direction;getOpposite()Lnet/minecraft/core/Direction;", shift = At.Shift.AFTER
            )
    )
    private static void lambda$setPlacedBy$0(AutoRequestData requestData, Player player, TableClothBlockEntity dcbe, CallbackInfo ci) {
        if(dcbe instanceof SideShelfBlockEntity ssbe)
            ssbe.checkFacing();
    }

    @SuppressWarnings("unused")
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
