package io.thijzert;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ClearDespawnReworked.MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    private static final int minFlashSpeed = 1;
    @ConfigEntry.Gui.Excluded
    private static final int maxFlashSpeed = 100;

    public boolean modEnabled = true;
    public boolean flashingEnabled = true;
    public int despawnAge = 6000; // game ticks
    @ConfigEntry.BoundedDiscrete(min = 1, max = 300)
    public int flashStartTime = 15; // seconds
    @ConfigEntry.BoundedDiscrete(min = minFlashSpeed, max = maxFlashSpeed)
    public int flashSpeed = 75; // how many times to flash in a second, needs to be reversed
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int disappearTime = 40; // percentage, needs to be divided by 100.0F
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int defaultItemShadowOpacity = 75; // percentage, needs to be divided by 100.0F
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int disappearItemShadowOpacity = 0; // percentage, needs to be divided by 100.0F

    public int getFlashStartTime() {
        return flashStartTime * 20;
    }

    public int getFlashSpeed() {
        return Math.max(2, minFlashSpeed + maxFlashSpeed - flashSpeed);
    }

    public float getDisappearTime() {
        return disappearTime / 100.0F;
    }

    public float getDefaultItemShadowOpacity() {
        return defaultItemShadowOpacity / 100.0F;
    }

    public float getDisappearItemShadowOpacity() {
        return disappearItemShadowOpacity / 100.0F;
    }
}
