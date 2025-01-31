package com.github.Debris.SitMod.event;

import com.github.Debris.SitMod.entity.EntitySit;
import com.github.Debris.SitMod.SitMod;
import com.google.common.eventbus.Subscribe;
import net.xiaoyu233.fml.reload.event.EntityRegisterEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;

public class Events {
    @Subscribe
    public void onEntityRegister(EntityRegisterEvent event) {
        event.register(EntitySit.class, SitMod.MOD_ID, "EntitySit", IdUtil.getNextEntityID());
    }
}
