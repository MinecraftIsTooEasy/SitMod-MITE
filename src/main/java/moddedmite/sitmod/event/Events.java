package moddedmite.sitmod.event;

import moddedmite.sitmod.entity.EntitySit;
import moddedmite.sitmod.SitMod;
import com.google.common.eventbus.Subscribe;
import net.xiaoyu233.fml.reload.event.EntityRegisterEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;

public class Events {
    @Subscribe
    public void onEntityRegister(EntityRegisterEvent event) {
        event.register(EntitySit.class, SitMod.MOD_ID, "EntitySit", IdUtil.getNextEntityID());
    }
}
