package dev.bscit.dunraiders.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class DunraidersCommands
{
    public static void initialize()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            // dispatcher.register(DeleteDimensionData.create());
        });
    }
}
