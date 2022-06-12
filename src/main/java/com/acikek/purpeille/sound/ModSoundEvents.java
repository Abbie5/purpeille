package com.acikek.purpeille.sound;

import com.acikek.purpeille.Purpeille;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {

    public static final SoundEvent ANCIENT_GATEWAY_TELEPORT = create("block.ancient_gateway.teleport");
    public static final SoundEvent LIGHTNING_BLOCK_PLACE = create("block.lightning_block.place");
    public static final SoundEvent LIGHTNING_BLOCK_BREAK = create("block.lightning_block.break");

    public static SoundEvent create(String id) {
        return new SoundEvent(Purpeille.id(id));
    }

    public static SoundEvent[] SOUNDS = {
            ANCIENT_GATEWAY_TELEPORT,
            LIGHTNING_BLOCK_PLACE,
            LIGHTNING_BLOCK_BREAK
    };

    public static void register() {
        for (SoundEvent sound : SOUNDS) {
            Registry.register(Registry.SOUND_EVENT, sound.getId(), sound);
        }
    }
}
