package com.zeromods.dupecontrols.mixin.client;

import com.zeromods.dupecontrols.binds.BindMap;
import com.zeromods.dupecontrols.binds.DupeBind;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "startGame", at = @At("RETURN"))
    public void impl$startGame(CallbackInfo callbackInfo) {
        BindMap.bindings.add(new DupeBind("key.attack", -99));
    }
}
