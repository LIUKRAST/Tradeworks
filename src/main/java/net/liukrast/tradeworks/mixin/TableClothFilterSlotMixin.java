package net.liukrast.tradeworks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.liukrast.tradeworks.TableClothPlacement;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "com.simibubi.create.content.logistics.tableCloth.TableClothFilterSlot")
public class TableClothFilterSlotMixin {

    @Unique
    private static final Vec3 tradeworks$DEFAULT = VecHelper.voxelSpace(13,11,14.60);

    @Shadow
    private TableClothBlockEntity be;

    @ModifyVariable(
            method = "getLocalOffset",
            at = @At("STORE"),
            name = "v"
    )
    private Vec3 getLocalOffset(Vec3 value, @Local(argsOnly = true) BlockState state) {
        if (!(state.getBlock() instanceof TableClothPlacement sb)) return value;
        return tradeworks$DEFAULT.add(sb.getPriceOffset(be, state).multiply(new Vec3(-1, 1, -1)));
    }

    @ModifyExpressionValue(method = "rotate", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/logistics/tableCloth/TableClothBlockEntity;sideOccluded:Z", opcode = Opcodes.GETFIELD))
    private boolean rotate(boolean original, @Local(argsOnly = true) BlockState state) {
        if(state.getBlock() instanceof TableClothPlacement) return false;
        return original;
    }
}
