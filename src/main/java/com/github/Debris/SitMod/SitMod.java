package com.github.Debris.SitMod;

import com.github.Debris.SitMod.event.Events;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.reload.event.MITEEvents;

public class SitMod implements ModInitializer {

    public static final String MOD_ID = "SitMod";

    public static final boolean debug = false;

    @Override
    public void onInitialize() {
        MITEEvents.MITE_EVENT_BUS.register(new Events());
    }
}