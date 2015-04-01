package us.ichun.mods.worldportals.common.core;

import us.ichun.mods.ichunutil.common.core.config.ConfigBase;

import java.io.File;

public class Config extends ConfigBase
{
    public Config(File file)
    {
        super(file);
    }

    @Override
    public String getModId()
    {
        return "worldportals";
    }

    @Override
    public String getModName()
    {
        return "WorldPortals";
    }
}
