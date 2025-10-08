package dev.bscit.dunraiders.block;

import dev.bscit.dunraiders.Dunraiders;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static dev.bscit.dunraiders.Dunraiders.MOD_ID;

public class DunraidersBlocks
{
    public static final LayerBlock SAND_PILE = register(
        "sand_pile",
        new LayerBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.PALE_YELLOW)
            .replaceable()
            .notSolid()
            .strength(0.5F)
            .sounds(BlockSoundGroup.SAND)
            .blockVision((state, world, pos) -> (int)state.get(LayerBlock.LAYERS) >= 8)
            .pistonBehavior(PistonBehavior.DESTROY)
        )
    );

    public static final LayerBlock RED_SAND_PILE = register(
        "red_sand_pile",
        new LayerBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.ORANGE)
            .replaceable()
            .notSolid()
            .strength(0.5F)
            .sounds(BlockSoundGroup.SAND)
            .blockVision((state, world, pos) -> (int)state.get(LayerBlock.LAYERS) >= 8)
            .pistonBehavior(PistonBehavior.DESTROY)
        )
    );

    private static <T extends Block> T register(String id, T block)
    {
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, id), block);
    }

    public static void initialize()
    {
        Dunraiders.LOGGER.info("Registering {} blocks", MOD_ID);
    }
}
