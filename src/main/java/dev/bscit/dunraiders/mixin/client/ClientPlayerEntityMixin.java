package dev.bscit.dunraiders.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.bscit.dunraiders.DunraidersConfig;
import dev.bscit.dunraiders.client.DunraidersClient;
import dev.bscit.dunraiders.components.DunraidersComponents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin
{
    @Inject(method = "tickMovement()V", at = @At(shift = At.Shift.AFTER, value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/network/ClientPlayerEntity;inSneakingPose:Z"))
    private void modifyPoseCondition(CallbackInfo ci, @Local PlayerAbilities playerAbilities)
    {
        if(!DunraidersConfig.getConfig().common.gameplay.disableCrouch)
            return;
        ClientPlayerEntity _this = (ClientPlayerEntity)(Object)this;
        _this.inSneakingPose = !playerAbilities.flying
            && !_this.isSwimming()
            && !_this.hasVehicle()
            && _this.canChangeIntoPose(EntityPose.CROUCHING)
            && (/*_this.isSneaking() || */!_this.isSleeping() && !_this.canChangeIntoPose(EntityPose.STANDING));
    }

    @ModifyVariable(method = "sendMovementPackets()V", index = 1, at = @At(value = "STORE"))
    private boolean modifyIsSneakingInSendMovementPackets(boolean value)
    {
        if(!DunraidersConfig.getConfig().common.gameplay.disableCrouch)
            return value;
        return false;
    }

    @ModifyVariable(method = "tickMovement()V", index = 2, at = @At(value = "STORE"))
    private boolean modifyIsSneakingInTickMovement(boolean value)
    {
        if(!DunraidersConfig.getConfig().common.gameplay.disableCrouch)
            return value;
        return false;
    }

    @ModifyReturnValue(method = "canStartSprinting()Z", at = @At(value = "RETURN"))
    private boolean enableMoreSprinting(boolean value)
    {
        ClientPlayerEntity _this = (ClientPlayerEntity)(Object)this;
        return !_this.isSprinting()
            && _this.isWalking()
            && _this.canSprint()
            /*&& !_this.isUsingItem()*/
            /*&& !_this.hasStatusEffect(StatusEffects.BLINDNESS)*/
            && (!_this.hasVehicle() || _this.canVehicleSprint(_this.getVehicle()))
            && !_this.isFallFlying();
    }

    // move speed tweaks
    @Inject(method = "tickMovement()V", at = @At(shift = At.Shift.AFTER, value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/input/Input;movementSideways:F"))
    private void movementSidewaysTweak(CallbackInfo ci)
    {
        ClientPlayerEntity _this = (ClientPlayerEntity)(Object)this;
        var item = _this.getActiveItem();
        if(item == null)
            return;
        var component = item.get(DunraidersComponents.SLOW_DOWN_ON_USE_COMPONENT_TYPE);
        if(component == null) // use default behavior
            return;

        _this.input.movementSideways *= (float)(5 * component);
    }

    @Inject(method = "tickMovement()V", at = @At(shift = At.Shift.AFTER, value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/input/Input;movementForward:F"))
    private void movementForwardTweak(CallbackInfo ci)
    {
        ClientPlayerEntity _this = (ClientPlayerEntity)(Object)this;
        var item = _this.getActiveItem();
        if(item == null)
            return;
        var component = item.get(DunraidersComponents.SLOW_DOWN_ON_USE_COMPONENT_TYPE);
        if(component == null) // use default behavior
            return;

        _this.input.movementForward *= (float)(5 * component);
    }
}
