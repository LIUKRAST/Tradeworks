package net.liukrast.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.liukrast.block.ShelfBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "com.simibubi.create.content.logistics.tableCloth.TableClothFilterSlot")
public class TableClothFilterSlotMixin {

    @Shadow
    private TableClothBlockEntity be;

    @ModifyReturnValue(method = "getLocalOffset", at = @At("RETURN"))
    private Vec3 getLocalOffset(Vec3 original, @Local(argsOnly = true) BlockState state) {
        if(!(state.getBlock() instanceof ShelfBlock sb)) return original;
        return sb.getValueBox(be, state);
    }

    @ModifyExpressionValue(method = "rotate", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/logistics/tableCloth/TableClothBlockEntity;sideOccluded:Z", opcode = Opcodes.GETFIELD))
    private boolean rotate(boolean original, @Local(argsOnly = true) BlockState state) {
        if(!(state.getBlock() instanceof ShelfBlock)) return original;
        return !state.getValue(ShelfBlock.TOP);
    }
}
