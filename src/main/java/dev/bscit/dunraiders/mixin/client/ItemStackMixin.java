package dev.bscit.dunraiders.mixin.client;

import com.google.common.collect.Lists;
import dev.bscit.dunraiders.Dunraiders;
import dev.bscit.dunraiders.client.DunraidersClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin
{
    @Unique
    private static final Text throwableTooltip = Text.translatable("dunraiders.tooltip.throwable").setStyle(Style.EMPTY.withColor(Formatting.YELLOW));

    @Inject(method = "getTooltip", order = 500, locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE", ordinal = 0, target = "java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true)
    public void onGetTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir, List<Text> list)
    {
        ItemStack thisObject = (ItemStack)(Object)this;

        boolean showTooltip = Screen.hasShiftDown();
        String[] split = Text.translatable(Dunraiders.MOD_ID + ".tooltip.holdForDetails", "$").getString().split("\\$");

        if(player != null)
        {
            list.add(
                1,
                Text.literal(split[0]).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY))
                    .append(Text.translatable("dunraiders.tooltip.keyShift").setStyle(Style.EMPTY.withColor(showTooltip ? Formatting.WHITE : Formatting.GRAY)))
                    .append(Text.literal(split[1]).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
            );

            if(!showTooltip)
            {
                cir.setReturnValue(list);
                return;
            }
            else
                list.add(2, Text.literal(""));
        }

        boolean isThrowable = thisObject.isIn(TagKey.of(Registries.ITEM.getKey(), Identifier.of("supplementaries", "throwable_bricks")))
                           || thisObject.isIn(TagKey.of(Registries.ITEM.getKey(), Identifier.of("kitchenprojectiles", "light_knives")));

        if(isThrowable)
        {
            list.add(throwableTooltip);
        }
    }
}
