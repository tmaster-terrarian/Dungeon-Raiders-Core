package dev.bscit.dunraiders.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.bscit.dunraiders.Dunraiders;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.World;
import net.minecraft.world.level.storage.LevelStorage;

import java.io.File;

// cursed asf and DOES NOT WORK
public class DeleteDimensionData
{
    public static LiteralArgumentBuilder<ServerCommandSource> create()
    {
        return CommandManager.literal("test_command")
            .executes(context -> {
                MinecraftServer server = context.getSource().getServer();
                CommandManager commandManager = server.getCommandManager();
                server.executeSync(() -> {
                    String command = "execute as @a in minecraft:overworld run tp @s 0 63 0";
                    commandManager.execute(commandManager.getDispatcher().parse(command, context.getSource()), command);

                    String worldFolder = server.getSavePath(WorldSavePath.ROOT).toAbsolutePath().toString();
                    worldFolder = worldFolder.substring(0, worldFolder.length() - 2);

                    File dir = new File(worldFolder + "/dimensions/dunraiders/swamplands/region");
                    if (dir.delete()) {
                        Dunraiders.LOGGER.info("Deleted the folder: {}", dir);
                    } else {
                        Dunraiders.LOGGER.info("Failed to delete the folder: {}", dir);
                    }
                });
                return 1;
            });
    }
}
