package net.liukrast.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.logistics.depot.DepotRenderer;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.render.CachedBuffers;
import net.liukrast.TableClothPlacement;
import net.liukrast.registry.TradeworksPartialModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShelfRenderer extends SmartBlockEntityRenderer<TableClothBlockEntity> {
    public ShelfRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(TableClothBlockEntity blockEntity, float partialTicks, PoseStack ms,
                              MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);
        List<ItemStack> stacks = blockEntity.getItemsForRender();
        float rotationInRadians = Mth.DEG_TO_RAD * (180 - blockEntity.facing.toYRot());
        var block = (TableClothPlacement)(blockEntity.getBlockState().getBlock());

        if (blockEntity.isShop()) {

            CachedBuffers
                    .partial(TradeworksPartialModels.SHELF_PRICE_TAG, blockEntity.getBlockState())
                    .rotateCentered(rotationInRadians, Direction.UP)
                    .translate(block.getPriceOffset(blockEntity, blockEntity.getBlockState()))
                    .light(light)
                    .overlay(overlay)
                    .renderInto(ms, buffer.getBuffer(RenderType.cutout()));
        }

        ms.pushPose();
        TransformStack.of(ms)
                .rotateCentered(rotationInRadians, Direction.UP);
        var axis = block.getRotatingItemsAxis();
        if(block instanceof ShelfBlock) {
            for (int i = 0; i < stacks.size(); i++) {
                ItemStack entry = stacks.get(i);
                ms.pushPose();
                ms.translate(0.5f, 5 / 16f, 0.5f);

                if (stacks.size() > 1) {
                    ms.mulPose(axis.rotationDegrees(i * (360f / stacks.size()) + 45f));
                    ms.translate(0, i % 2 == 0 ? -0.005 : 0, 5 / 16f);
                    ms.mulPose(axis.rotationDegrees(-i * (360f / stacks.size()) - 45f));
                }

                BakedModel bakedModel = Minecraft.getInstance()
                        .getItemRenderer()
                        .getModel(entry, null, null, 0);
                boolean blockItem = bakedModel.isGui3d();
                if (!blockItem)
                    TransformStack.of(ms)
                            .rotate(-rotationInRadians + Mth.PI, Direction.UP);

                DepotRenderer.renderItem(blockEntity.getLevel(), ms, buffer, light, OverlayTexture.NO_OVERLAY, entry, 0,
                        null, Vec3.atCenterOf(blockEntity.getBlockPos()), true);
                ms.popPose();
            }
        } else {
            for (int i = 0; i < stacks.size(); i++) {
                ItemStack entry = stacks.get(i);
                ms.pushPose();
                ms.translate(12/16f, 3.8 / 16f, 11/16f);
                ms.translate(-(i%2)/2f, (i>>1)/2f, i == 3 ? 2.2/16f : 0);

                BakedModel bakedModel = Minecraft.getInstance()
                        .getItemRenderer()
                        .getModel(entry, null, null, 0);
                boolean blockItem = bakedModel.isGui3d();
                if (!blockItem)
                    TransformStack.of(ms)
                            .rotate(-rotationInRadians + Mth.PI, Direction.UP);

                DepotRenderer.renderItem(blockEntity.getLevel(), ms, buffer, light, OverlayTexture.NO_OVERLAY, entry, 0,
                        null, Vec3.atCenterOf(blockEntity.getBlockPos()), true);
                ms.popPose();
            }
        }

        ms.popPose();
    }
}
