package dev.bscit.dunraiders.client;

import dev.bscit.dunraiders.DunraidersConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;

import java.util.function.Supplier;

public class DunraidersClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        AutoConfig.register(
            DunraidersConfig.class,
            PartitioningSerializer.wrap(JanksonConfigSerializer::new)
        );
    }
}
