package dev.bscit.dunraiders.mixin;

import dev.bscit.dunraiders.attribute.DunraidersAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @Shadow
    public abstract double getAttributeValue(RegistryEntry<EntityAttribute> attribute);

    @Inject(method = "createLivingAttributes", require = 1, allow = 1, at = @At("RETURN"))
    private static void additionalEntityAttributes$addPlayerAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info)
    {
        info.getReturnValue().add(DunraidersAttributes.IMMUNITY_TICKS);
    }

    // iframes
    @ModifyConstant(method = {"damage", "animateDamage", "onDamaged"}, constant = @Constant(intValue = 10, ordinal = 0))
    private int modifyMaxHurtTime(int value)
    {
        return (int)getAttributeValue(DunraidersAttributes.IMMUNITY_TICKS);
    }

    @ModifyConstant(method = "damage", constant = @Constant(floatValue = 10f, ordinal = 0))
    private float modifyRegenCheck(float constant)
    {
        return (float)getAttributeValue(DunraidersAttributes.IMMUNITY_TICKS);
    }

    @ModifyConstant(method = {"damage", "onDamaged"}, constant = @Constant(intValue = 20, ordinal = 0))
    private int modifyTimeUntilRegen(int constant)
    {
        return 5 + (int)getAttributeValue(DunraidersAttributes.IMMUNITY_TICKS);
    }
}
