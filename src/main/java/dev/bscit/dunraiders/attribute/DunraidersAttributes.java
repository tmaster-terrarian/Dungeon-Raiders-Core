package dev.bscit.dunraiders.attribute;

import dev.bscit.dunraiders.Dunraiders;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute.Category;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class DunraidersAttributes
{
    public static final RegistryEntry<EntityAttribute> IMMUNITY_TICKS = register(
        "immunity_ticks",
        new ClampedEntityAttribute("attribute.dunraiders.name.immunity_ticks", 5.0, 1.0, Double.MAX_VALUE)
            .setTracked(true)
            .setCategory(Category.POSITIVE)
    );

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute)
    {
        return Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(Dunraiders.MOD_ID, id), attribute);
    }

    public static void initialize()
    {
        Dunraiders.LOGGER.info("Registering {} attributes", Dunraiders.MOD_ID);
    }
}
