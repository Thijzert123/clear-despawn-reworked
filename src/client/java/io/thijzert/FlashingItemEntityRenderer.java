package io.thijzert;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;

public class FlashingItemEntityRenderer extends ItemEntityRenderer {
    public FlashingItemEntityRenderer(final EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(final ItemEntity entity,
                       final float entityYaw,
                       final float partialTicks,
                       final MatrixStack matrixStack,
                       final VertexConsumerProvider vertexConsumerProvider,
                       final int packedLight) {
        if (ClearDespawnReworkedClient.config.modEnabled) {
            if (ClearDespawnReworkedClient.config.flashingEnabled) {
                final int remainingTime = ClearDespawnReworkedClient.config.despawnAge - entity.getItemAge();
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
        super.render(entity, entityYaw, partialTicks, matrixStack, vertexConsumerProvider, packedLight);
    }

    public static class Factory implements EntityRendererFactory<ItemEntity> {
        @Override
        public EntityRenderer<ItemEntity> create(final Context context) {
            return new FlashingItemEntityRenderer(context);
        }
    }
}
