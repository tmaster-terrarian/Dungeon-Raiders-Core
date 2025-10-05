package dev.bscit.dunraiders.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.bscit.dunraiders.DunraidersConfig;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    // disable view bobbing if not in first person
    @Definition(id = "client", field = "Lnet/minecraft/client/render/GameRenderer;client:Lnet/minecraft/client/MinecraftClient;")
    @Definition(id = "getCameraEntity", method = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;")
    @Definition(id = "PlayerEntity", type = PlayerEntity.class)
    @Expression("this.client.getCameraEntity() instanceof PlayerEntity")
    @ModifyExpressionValue(method = "bobView", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean shouldBobView(boolean original)
    {
        if(!DunraidersConfig.getConfig().client.steadyThirdPerson)
            return original;

        GameRenderer _this = (GameRenderer)(Object)this;
        return original && _this.getClient().options.getPerspective().isFirstPerson();
    }
}
