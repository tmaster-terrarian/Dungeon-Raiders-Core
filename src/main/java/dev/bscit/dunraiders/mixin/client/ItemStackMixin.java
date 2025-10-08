package dev.bscit.dunraiders.mixin.client;

import com.google.common.collect.Lists;
import dev.bscit.dunraiders.Dunraiders;
import dev.bscit.dunraiders.DunraidersConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin
{
    @Unique
    private static final Text throwableTooltip = Text.translatable("dunraiders.tooltip.throwable").setStyle(Style.EMPTY.withColor(Formatting.YELLOW));

    @Unique
    private static final Text accessorySlotTooltip = Text.translatable( "accessories.slot.tooltip.singular").formatted(Formatting.GRAY);

    @Inject(method = "getTooltip", order = 2000, at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    public void onGetTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir)
    {
        ItemStack thisObject = (ItemStack)(Object)this;
        List<Text> list = cir.getReturnValue();

        boolean isThrowable = thisObject.isIn(TagKey.of(Registries.ITEM.getKey(), Identifier.of("supplementaries", "throwable_bricks")))
            || thisObject.isIn(TagKey.of(Registries.ITEM.getKey(), Identifier.of("kitchenprojectiles", "light_knives")));

        if(isThrowable)
        {
            list.add(1, throwableTooltip);
        }

        if(!DunraidersConfig.getConfig().client.gui.shortenTooltips)
            return;

        boolean EMIloaded = FabricLoader.getInstance().isModLoaded("emi");

        if(player != null && list.size() > (EMIloaded ? 2 : 1))
        {
            String[] split = Text.translatable(Dunraiders.MOD_ID + ".tooltip.holdForDetails", "$").getString().split("\\$");
            List<Text> newList = Lists.<Text>newArrayList();
            boolean showTooltip = Screen.hasShiftDown();

            newList.add(
                Text.literal(split[0]).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY))
                    .append(Text.translatable("dunraiders.tooltip.keyShift").setStyle(Style.EMPTY.withColor(showTooltip ? Formatting.WHITE : Formatting.GRAY)))
                    .append(Text.literal(split[1]).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
            );

            if(!showTooltip)
            {
                // item name
                newList.addFirst(list.getFirst());

                // accessory slot
                Text accessorySlot = list.stream()
                    .filter(text -> text.contains(accessorySlotTooltip))
                    .findFirst()
                    .orElse(null);

                if(accessorySlot != null)
                {
                    newList.add(Text.literal(" "));
                    newList.add(accessorySlot);
                }

                // EMI mod id tooltip
                if(EMIloaded)
                {
                    newList.add(list.stream()
                        .filter(text -> text.getStyle().isItalic() && text.getStyle().getColor() == TextColor.fromFormatting(Formatting.BLUE))
                        .findFirst()
                        .orElse(null)
                    );
                }

                cir.setReturnValue(newList);
            }
            else
            {
                newList.add(Text.literal(" "));
                list.addAll(1, newList);
            }
        }
    }
}
