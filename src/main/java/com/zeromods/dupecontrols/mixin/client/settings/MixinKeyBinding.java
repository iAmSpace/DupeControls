package com.zeromods.dupecontrols.mixin.client.settings;

import com.zeromods.dupecontrols.binds.BindMap;
import com.zeromods.dupecontrols.bridge.KeyBindingBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.IntHashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public class MixinKeyBinding implements KeyBindingBridge {
    @Shadow @Final private static IntHashMap<KeyBinding> hash;
    @Shadow private boolean pressed;

    /**
     * @author iAmSpace
     * @reason DupeControls
     */
    @Overwrite
    public static void setKeyBindState(int keyCode, boolean pressed) {
        if (keyCode != 0) {
            KeyBinding keybinding = hash.lookup(keyCode);
            String dupe = BindMap.getDescriptionForKeycode(keyCode); // ADDED

            if (keybinding != null)
                ((KeyBindingBridge) keybinding).bridge$setPressed(pressed); // MODIFIED

            // START ADDED
            if (!dupe.equals("")) {
                for (KeyBinding kb : Minecraft.getMinecraft().gameSettings.keyBindings) {
                    if (kb.getKeyDescription().equals(dupe)) {
                        ((KeyBindingBridge) kb).bridge$setPressed(pressed);
                    }
                }
            }
            // END ADDED
        }
    }

    public void bridge$setPressed(boolean b) {
        this.pressed = b;
    }
}
