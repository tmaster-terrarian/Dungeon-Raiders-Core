package dev.bscit.dunraiders;

import dev.bscit.dunraiders.attribute.DunraidersAttributes;
import dev.bscit.dunraiders.component.DunraidersComponents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dunraiders implements ModInitializer
{
    public static final String MOD_ID = "dunraiders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize()
    {
        DunraidersComponents.initialize();
        DunraidersAttributes.initialize();
        LOGGER.info("Dungeon Raiders Core loaded");
    }
}
