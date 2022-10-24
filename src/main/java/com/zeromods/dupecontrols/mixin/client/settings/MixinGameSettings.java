package com.zeromods.dupecontrols.mixin.client.settings;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.world.EnumDifficulty;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

@Mixin(GameSettings.class)
public abstract class MixinGameSettings {
    @Shadow private File optionsFile;

    @Shadow private Map<SoundCategory, Float> mapSoundLevels;

    @Shadow public float mouseSensitivity;

    @Shadow public float fovSetting;

    @Shadow public float gammaSetting;

    @Shadow public float saturation;

    @Shadow public boolean invertMouse;

    @Shadow public int renderDistanceChunks;

    @Shadow public int guiScale;

    @Shadow public int particleSetting;

    @Shadow public boolean viewBobbing;

    @Shadow public boolean anaglyph;

    @Shadow public int limitFramerate;

    @Shadow public boolean fboEnable;

    @Shadow public EnumDifficulty difficulty;

    @Shadow public boolean fancyGraphics;

    @Shadow public int ambientOcclusion;

    @Shadow public int clouds;

    @Shadow public List<String> resourcePacks;

    @Shadow @Final private static Gson gson;

    @Shadow @Final private static ParameterizedType typeListString;

    @Shadow public List<String> incompatibleResourcePacks;

    @Shadow public String lastServer;

    @Shadow public String language;

    @Shadow public EntityPlayer.EnumChatVisibility chatVisibility;

    @Shadow public boolean chatColours;

    @Shadow public boolean chatLinks;

    @Shadow public boolean chatLinksPrompt;

    @Shadow public float chatOpacity;

    @Shadow public boolean snooperEnabled;

    @Shadow public boolean fullScreen;

    @Shadow public boolean enableVsync;

    @Shadow public boolean useVbo;

    @Shadow public boolean hideServerAddress;

    @Shadow public boolean advancedItemTooltips;

    @Shadow public boolean pauseOnLostFocus;

    @Shadow public boolean touchscreen;

    @Shadow public int overrideHeight;

    @Shadow public int overrideWidth;

    @Shadow public boolean heldItemTooltips;

    @Shadow public float chatHeightFocused;

    @Shadow public float chatHeightUnfocused;

    @Shadow protected abstract float parseFloat(String str);

    @Shadow public float chatScale;

    @Shadow public float chatWidth;

    @Shadow public boolean showInventoryAchievementHint;

    @Shadow public int mipmapLevels;

    @Shadow public float streamBytesPerPixel;

    @Shadow public float streamMicVolume;

    @Shadow public float streamGameVolume;

    @Shadow public float streamKbps;

    @Shadow public float streamFps;

    @Shadow public int streamCompression;

    @Shadow public boolean streamSendMetadata;

    @Shadow public String streamPreferredServer;

    @Shadow public int streamChatEnabled;

    @Shadow public int streamChatUserFilter;

    @Shadow public int streamMicToggleBehavior;

    @Shadow public boolean forceUnicodeFont;

    @Shadow public boolean allowBlockAlternatives;

    @Shadow public boolean reducedDebugInfo;

    @Shadow public boolean useNativeTransport;

    @Shadow public boolean entityShadows;

    @Shadow public boolean realmsNotifications;

    @Shadow public KeyBinding[] keyBindings;

    @Shadow @Final private static Logger logger;

    @Shadow public abstract void setModelPartEnabled(EnumPlayerModelParts modelPart, boolean enable);

    /**
     * @author iAmSpace
     * @reason DupeControls
     */
    @Overwrite
    public void loadOptions()
    {
        try
        {
            if (!this.optionsFile.exists())
            {
                return;
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.optionsFile));
            String s;
            this.mapSoundLevels.clear();

            while ((s = bufferedreader.readLine()) != null)
            {
                try
                {
                    String[] astring = s.split(":");

                    if (astring[0].equals("mouseSensitivity"))
                        this.mouseSensitivity = this.parseFloat(astring[1]);

                    if (astring[0].equals("fov"))
                        this.fovSetting = this.parseFloat(astring[1]) * 40.0F + 70.0F;

                    if (astring[0].equals("gamma"))
                        this.gammaSetting = this.parseFloat(astring[1]);

                    if (astring[0].equals("saturation"))
                        this.saturation = this.parseFloat(astring[1]);

                    if (astring[0].equals("invertYMouse"))
                        this.invertMouse = astring[1].equals("true");

                    if (astring[0].equals("renderDistance"))
                        this.renderDistanceChunks = Integer.parseInt(astring[1]);

                    if (astring[0].equals("guiScale"))
                        this.guiScale = Integer.parseInt(astring[1]);

                    if (astring[0].equals("particles"))
                        this.particleSetting = Integer.parseInt(astring[1]);

                    if (astring[0].equals("bobView"))
                        this.viewBobbing = astring[1].equals("true");

                    if (astring[0].equals("anaglyph3d"))
                        this.anaglyph = astring[1].equals("true");

                    if (astring[0].equals("maxFps"))
                        this.limitFramerate = Integer.parseInt(astring[1]);

                    if (astring[0].equals("fboEnable"))
                        this.fboEnable = astring[1].equals("true");

                    if (astring[0].equals("difficulty"))
                        this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(astring[1]));

                    if (astring[0].equals("fancyGraphics"))
                        this.fancyGraphics = astring[1].equals("true");

                    if (astring[0].equals("ao")) {
                        if (astring[1].equals("true"))
                            this.ambientOcclusion = 2;
                        else if (astring[1].equals("false"))
                            this.ambientOcclusion = 0;
                        else
                            this.ambientOcclusion = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("renderClouds")) {
                        switch (astring[1]) {
                            case "true":
                                this.clouds = 2;
                                break;
                            case "false":
                                this.clouds = 0;
                                break;
                            case "fast":
                                this.clouds = 1;
                                break;
                        }
                    }

                    if (astring[0].equals("resourcePacks")) {
                        this.resourcePacks = gson.fromJson(s.substring(s.indexOf(58) + 1), typeListString);
                        if (this.resourcePacks == null)
                            this.resourcePacks = Lists.newArrayList();
                    }

                    if (astring[0].equals("incompatibleResourcePacks")) {
                        this.incompatibleResourcePacks = gson.fromJson(s.substring(s.indexOf(58) + 1), typeListString);
                        if (this.incompatibleResourcePacks == null)
                            this.incompatibleResourcePacks = Lists.newArrayList();
                    }

                    if (astring[0].equals("lastServer") && astring.length >= 2)
                        this.lastServer = s.substring(s.indexOf(58) + 1);

                    if (astring[0].equals("lang") && astring.length >= 2)
                        this.language = astring[1];

                    if (astring[0].equals("chatVisibility"))
                        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(astring[1]));

                    if (astring[0].equals("chatColors"))
                        this.chatColours = astring[1].equals("true");

                    if (astring[0].equals("chatLinks"))
                        this.chatLinks = astring[1].equals("true");

                    if (astring[0].equals("chatLinksPrompt"))
                        this.chatLinksPrompt = astring[1].equals("true");

                    if (astring[0].equals("chatOpacity"))
                        this.chatOpacity = this.parseFloat(astring[1]);

                    if (astring[0].equals("snooperEnabled"))
                        this.snooperEnabled = astring[1].equals("true");

                    if (astring[0].equals("fullscreen"))
                        this.fullScreen = astring[1].equals("true");

                    if (astring[0].equals("enableVsync"))
                        this.enableVsync = astring[1].equals("true");

                    if (astring[0].equals("useVbo"))
                        this.useVbo = astring[1].equals("true");

                    if (astring[0].equals("hideServerAddress"))
                        this.hideServerAddress = astring[1].equals("true");

                    if (astring[0].equals("advancedItemTooltips"))
                        this.advancedItemTooltips = astring[1].equals("true");

                    if (astring[0].equals("pauseOnLostFocus"))
                        this.pauseOnLostFocus = astring[1].equals("true");

                    if (astring[0].equals("touchscreen"))
                        this.touchscreen = astring[1].equals("true");

                    if (astring[0].equals("overrideHeight"))
                        this.overrideHeight = Integer.parseInt(astring[1]);

                    if (astring[0].equals("overrideWidth"))
                        this.overrideWidth = Integer.parseInt(astring[1]);

                    if (astring[0].equals("heldItemTooltips"))
                        this.heldItemTooltips = astring[1].equals("true");

                    if (astring[0].equals("chatHeightFocused"))
                        this.chatHeightFocused = this.parseFloat(astring[1]);

                    if (astring[0].equals("chatHeightUnfocused"))
                        this.chatHeightUnfocused = this.parseFloat(astring[1]);

                    if (astring[0].equals("chatScale"))
                        this.chatScale = this.parseFloat(astring[1]);

                    if (astring[0].equals("chatWidth"))
                        this.chatWidth = this.parseFloat(astring[1]);

                    if (astring[0].equals("showInventoryAchievementHint"))
                        this.showInventoryAchievementHint = astring[1].equals("true");

                    if (astring[0].equals("mipmapLevels"))
                        this.mipmapLevels = Integer.parseInt(astring[1]);

                    if (astring[0].equals("streamBytesPerPixel"))
                        this.streamBytesPerPixel = this.parseFloat(astring[1]);

                    if (astring[0].equals("streamMicVolume"))
                        this.streamMicVolume = this.parseFloat(astring[1]);

                    if (astring[0].equals("streamSystemVolume"))
                        this.streamGameVolume = this.parseFloat(astring[1]);

                    if (astring[0].equals("streamKbps"))
                        this.streamKbps = this.parseFloat(astring[1]);

                    if (astring[0].equals("streamFps"))
                        this.streamFps = this.parseFloat(astring[1]);

                    if (astring[0].equals("streamCompression"))
                        this.streamCompression = Integer.parseInt(astring[1]);

                    if (astring[0].equals("streamSendMetadata"))
                        this.streamSendMetadata = astring[1].equals("true");

                    if (astring[0].equals("streamPreferredServer") && astring.length >= 2)
                        this.streamPreferredServer = s.substring(s.indexOf(58) + 1);

                    if (astring[0].equals("streamChatEnabled"))
                        this.streamChatEnabled = Integer.parseInt(astring[1]);

                    if (astring[0].equals("streamChatUserFilter"))
                        this.streamChatUserFilter = Integer.parseInt(astring[1]);

                    if (astring[0].equals("streamMicToggleBehavior"))
                        this.streamMicToggleBehavior = Integer.parseInt(astring[1]);

                    if (astring[0].equals("forceUnicodeFont"))
                        this.forceUnicodeFont = astring[1].equals("true");

                    if (astring[0].equals("allowBlockAlternatives"))
                        this.allowBlockAlternatives = astring[1].equals("true");

                    if (astring[0].equals("reducedDebugInfo"))
                        this.reducedDebugInfo = astring[1].equals("true");

                    if (astring[0].equals("useNativeTransport"))
                        this.useNativeTransport = astring[1].equals("true");

                    if (astring[0].equals("entityShadows"))
                        this.entityShadows = astring[1].equals("true");

                    if (astring[0].equals("realmsNotifications"))
                        this.realmsNotifications = astring[1].equals("true");

                    for (KeyBinding keybinding : this.keyBindings) {
                        if (astring[0].equals("key_" + keybinding.getKeyDescription())) {
                            keybinding.setKeyCode(Integer.parseInt(astring[1]));
                        }
                    }

                    for (SoundCategory soundcategory : SoundCategory.values()) {
                        if (astring[0].equals("soundCategory_" + soundcategory.getCategoryName())) {
                            this.mapSoundLevels.put(soundcategory, this.parseFloat(astring[1]));
                        }
                    }

                    for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
                        if (astring[0].equals("modelPart_" + enumplayermodelparts.getPartName())) {
                            this.setModelPartEnabled(enumplayermodelparts, astring[1].equals("true"));
                        }
                    }
                }
                catch (Exception var8) {
                    logger.warn("Skipping bad option: " + s);
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader.close();
        }
        catch (Exception exception)
        {
            logger.error("Failed to load options", exception);
        }
    }
}
