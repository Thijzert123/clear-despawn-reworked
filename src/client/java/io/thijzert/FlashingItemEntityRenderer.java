package io.thijzert;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;

public class FlashingItemEntityRenderer extends ItemEntityRenderer {
    private ItemEntity entity_;

    public FlashingItemEntityRenderer(final EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void updateRenderState(final ItemEntity itemEntity, final ItemEntityRenderState itemEntityRenderState, float f) {
        entity_ = itemEntity;
        super.updateRenderState(itemEntity, itemEntityRenderState, f);
    }

    @Override
    public void render(final ItemEntityRenderState state,
                       final MatrixStack matrixStack,
                       final VertexConsumerProvider vertexConsumerProvider,
                       final int packedLight) {
        if (ClearDespawnReworkedClient.config.modEnabled) {
            if (ClearDespawnReworkedClient.config.flashingEnabled) {
                final int remainingTime = ClearDespawnReworkedClient.config.despawnAge - entity_.getItemAge();
                if (remainingTime < ClearDespawnReworkedClient.config.getFlashStartTime() && remainingTime > 0) {
                    int flashSpeed = ClearDespawnReworkedClient.config.getFlashSpeed();
                    if (ClearDespawnReworkedClient.config.urgentFlashing) {
                        flashSpeed = Math.max(3, remainingTime / ClearDespawnReworkedClient.config.getFlashSpeed());
                    }
                    if (remainingTime % flashSpeed <
                            ClearDespawnReworkedClient.config.getDisappearTime() * flashSpeed) {
                        this.shadowOpacity = ClearDespawnReworkedClient.config.getDisappearItemShadowOpacity();
                        return;
                    }
                }
            }
            this.shadowOpacity = ClearDespawnReworkedClient.config.getDefaultItemShadowOpacity();
        } else {
            this.shadowOpacity = 0.75F;
        }
        super.render(state, matrixStack, vertexConsumerProvider, packedLight);
    }

    public static class Factory implements EntityRendererFactory<ItemEntity> {
        @Override
        public EntityRenderer<ItemEntity, ?> create(final Context context) {
            return new FlashingItemEntityRenderer(context);
        }
    }
}
