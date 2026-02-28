package net.liukrast.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.liukrast.block.ShelfBlock;
import net.minecraft.core.Direction;
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
        if(!(state.getBlock() instanceof ShelfBlock)) return original;
        Vec3 v = !state.getValue(ShelfBlock.TOP) ? VecHelper.voxelSpace(8, 2.75, 13.25) : VecHelper.voxelSpace(12, 12, 14.75);
        return VecHelper.rotateCentered(v, -be.facing.toYRot(), Direction.Axis.Y);
    }

    @ModifyExpressionValue(method = "rotate", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/logistics/tableCloth/TableClothBlockEntity;sideOccluded:Z", opcode = Opcodes.GETFIELD))
    private boolean rotate(boolean original, @Local(argsOnly = true) BlockState state) {
        if(!(state.getBlock() instanceof ShelfBlock)) return original;
        return !state.getValue(ShelfBlock.TOP);
    }
}
