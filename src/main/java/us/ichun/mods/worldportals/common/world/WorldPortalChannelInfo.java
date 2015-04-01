package us.ichun.mods.worldportals.common.world;

import net.minecraft.util.BlockPos;

public class WorldPortalChannelInfo
{
    public final String channel;
    public final BlockPos pos;

    public WorldPortalChannelInfo(String channel, BlockPos pos)
    {
        this.channel = channel;
        this.pos = pos;
    }
}
