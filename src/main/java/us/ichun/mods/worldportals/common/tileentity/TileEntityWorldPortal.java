package us.ichun.mods.worldportals.common.tileentity;

import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalCarrier;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalInfo;

public class TileEntityWorldPortal extends WorldPortalCarrier
{
    public WorldPortalInfo portalInfo;

    public TileEntityWorldPortal()
    {
        portalInfo = new WorldPortalInfo();
        portalInfo.setParent(this);
    }

    @Override
    public WorldPortalInfo getPortalInfo()
    {
        return portalInfo;
    }
}
