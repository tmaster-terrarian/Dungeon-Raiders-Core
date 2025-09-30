package dev.bscit.dunraiders;

import dev.bscit.dunraiders.commands.DunraidersCommands;
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
        DunraidersCommands.initialize();

        LOGGER.info("dunraiders loaded");
    }
}
