package moddedmite.sitmod;

import moddedmite.sitmod.api.SitModPlugin;
import moddedmite.sitmod.event.Events;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.reload.event.MITEEvents;

import java.util.Collection;
import java.util.List;

public class SitMod implements ModInitializer {

    public static final String MOD_ID = "SitMod";

    public static final boolean debug = false;

    public static List<SitModPlugin> PLUGINS = null;

    @Override
    public void onInitialize() {
        MITEEvents.MITE_EVENT_BUS.register(new Events());
        PLUGINS = FishModLoader.getEntrypointContainers("sitmod", SitModPlugin.class).stream().map(EntrypointContainer::getEntrypoint).toList();
    }
}