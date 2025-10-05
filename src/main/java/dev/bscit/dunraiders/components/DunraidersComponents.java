package dev.bscit.dunraiders.components;

import com.mojang.serialization.Codec;
import dev.bscit.dunraiders.Dunraiders;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DunraidersComponents
{
    public static final ComponentType<Double> SLOW_DOWN_ON_USE_COMPONENT_TYPE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        Identifier.of(Dunraiders.MOD_ID, "slow_down_on_use"),
        ComponentType.<Double>builder().codec(Codec.doubleRange(0, Double.MAX_VALUE)).build()
    );

    public static void initialize()
    {
        Dunraiders.LOGGER.info("Registering {} components", Dunraiders.MOD_ID);
    }
}
