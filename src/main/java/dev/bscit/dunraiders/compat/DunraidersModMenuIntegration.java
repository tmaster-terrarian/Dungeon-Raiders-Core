package dev.bscit.dunraiders.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.bscit.dunraiders.DunraidersConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class DunraidersModMenuIntegration implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(DunraidersConfig.class, parent).get();
    }
}
