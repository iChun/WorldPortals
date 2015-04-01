package us.ichun.mods.worldportals.common.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.ichun.mods.ichunutil.client.gui.window.Window;
import us.ichun.mods.ichunutil.common.core.network.AbstractPacket;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalLocation;
import us.ichun.mods.worldportals.client.gui.GuiWorldPortalSettings;
import us.ichun.mods.worldportals.common.WorldPortals;
import us.ichun.mods.worldportals.common.world.WorldPortalChannelInfo;
import us.ichun.mods.worldportals.common.world.WorldPortalsSaveData;

import java.util.ArrayList;

public class PacketChannelList extends AbstractPacket
{
    public World world;
    public WorldPortalLocation loc;

    public ArrayList<WorldPortalChannelInfo> locs;

    public PacketChannelList(){}

    public PacketChannelList(World world, WorldPortalLocation loc)
    {
        this.world = world;
        this.loc = loc;
    }

    @Override
    public void writeTo(ByteBuf buffer, Side side)
    {
        WorldPortalsSaveData saveData = WorldPortals.eventHandler.worldSaveDataMap.get(world);
        if(saveData != null)
        {
            buffer.writeInt(Math.max(0, saveData.worldPortalLocations.size() - 1));
            for(int i = 0; i < saveData.worldPortalLocations.size(); i++)
            {
                WorldPortalLocation loc = saveData.worldPortalLocations.get(i);
                if(loc.equals(this.loc))
                {
                    continue;
                }
                ByteBufUtils.writeUTF8String(buffer, loc.info.channelName);
                buffer.writeLong(loc.pos.toLong());
            }
        }
        else
        {
            buffer.writeInt(0);
        }
    }

    @Override
    public void readFrom(ByteBuf buffer, Side side)
    {
        locs = new ArrayList<WorldPortalChannelInfo>();

        int count = buffer.readInt();
        for(int i = 0; i < count; i++)
        {
            locs.add(new WorldPortalChannelInfo(ByteBufUtils.readUTF8String(buffer), BlockPos.fromLong(buffer.readLong())));
        }
    }

    @Override
    public void execute(Side side, EntityPlayer player)
    {
        handleClient();
    }

    @SideOnly(Side.CLIENT)
    public void handleClient()
    {
        if(Minecraft.getMinecraft().currentScreen instanceof GuiWorldPortalSettings)
        {
            GuiWorldPortalSettings portalSettings = (GuiWorldPortalSettings)Minecraft.getMinecraft().currentScreen;
            ArrayList<Window> level = portalSettings.levels.get(1);
            for(Window window : level)
            {
                //TODO add channels
            }
        }
    }
}
