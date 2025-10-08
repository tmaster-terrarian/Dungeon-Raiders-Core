package dev.bscit.dunraiders.item;

import dev.bscit.dunraiders.Dunraiders;
import dev.bscit.dunraiders.attribute.DunraidersAttributes;
import dev.bscit.dunraiders.block.DunraidersBlocks;
import io.wispforest.accessories.api.components.AccessoriesDataComponents;
import io.wispforest.accessories.api.components.AccessoryItemAttributeModifiers;
import io.wispforest.accessories.api.components.AccessorySlotValidationComponent;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static dev.bscit.dunraiders.Dunraiders.MOD_ID;

public class DunraidersItems
{
    public static final RegistryKey<ItemGroup> DUNRAIDERS_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "items"));

    public static final ItemGroup DUNRAIDERS_ITEM_GROUP = FabricItemGroup.builder()
        .icon(() -> new ItemStack(Items.CACTUS))
        .displayName(Text.translatable("itemGroup.dunraiders.items"))
        .build();

    public static final Item IFRAME_TEST_ITEM = register(
        "iframe_test",
        new Item(new Item.Settings()
            .maxCount(1)
            .component(AccessoriesDataComponents.SLOT_VALIDATION, AccessorySlotValidationComponent.EMPTY
                .addValidSlot("spell_trinket")
            )
            .component(AccessoriesDataComponents.ATTRIBUTES, AccessoryItemAttributeModifiers.builder()
                .addForSlot(
                    DunraidersAttributes.IMMUNITY_TICKS,
                    new EntityAttributeModifier(
                        Identifier.of(MOD_ID, "bonus_iframes"),
                        5.0,
                        Operation.ADD_VALUE
                    ),
                    "spell_trinket",
                    true
                )
                .build()
            )
        )
    );

    public static final BlockItem SAND_PILE = register(
        "sand_pile",
        new BlockItem(DunraidersBlocks.SAND_PILE, new Item.Settings()
            .maxCount(64)
        )
    );

    public static final BlockItem RED_SAND_PILE = register(
        "red_sand_pile",
        new BlockItem(DunraidersBlocks.RED_SAND_PILE, new Item.Settings()
            .maxCount(64)
        )
    );

    private static <T extends Item> T register(String id, T item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
    }

    public static void initialize()
    {
        Dunraiders.LOGGER.info("Registering {} items", MOD_ID);

        Registry.register(Registries.ITEM_GROUP, DUNRAIDERS_ITEM_GROUP_KEY, DUNRAIDERS_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(DUNRAIDERS_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(IFRAME_TEST_ITEM);
            itemGroup.add(SAND_PILE);
            itemGroup.add(RED_SAND_PILE);
        });
    }
}
