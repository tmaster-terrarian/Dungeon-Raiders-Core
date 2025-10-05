package dev.bscit.dunraiders;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "dunraiders")
public class DunraidersConfig extends PartitioningSerializer.GlobalData
{
    public static DunraidersConfig getConfig()
    {
        return AutoConfig.getConfigHolder(DunraidersConfig.class).getConfig();
    }

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public ClientOptions client = new ClientOptions();

    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject
    public CommonOptions common = new CommonOptions();

    @Config(name = "client")
    public static class ClientOptions implements ConfigData
    {
        @ConfigEntry.Gui.Tooltip
        public boolean shortenTooltips = true;

        @ConfigEntry.Gui.Tooltip
        public boolean steadyThirdPerson = true;
    }

    @Config(name = "common")
    public static class CommonOptions implements ConfigData
    {
        public boolean disableCrouch = false;
    }
}
