package us.ichun.mods.worldportals.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import us.ichun.mods.ichunutil.common.core.network.ChannelHandler;
import us.ichun.mods.worldportals.common.WorldPortals;
import us.ichun.mods.worldportals.common.block.BlockWorldPortal;
import us.ichun.mods.worldportals.common.packet.PacketChannelList;
import us.ichun.mods.worldportals.common.packet.PacketRequestChannels;
import us.ichun.mods.worldportals.common.packet.PacketWorldPortalInfo;
import us.ichun.mods.worldportals.common.tileentity.TileEntityWorldPortal;

public class CommonProxy
{
    public void preInit()
    {
        WorldPortals.blockWorldPortal = (new BlockWorldPortal()).setCreativeTab(CreativeTabs.tabDecorations).setUnlocalizedName("worldportals.block.worldportal");

        GameRegistry.registerBlock(WorldPortals.blockWorldPortal, "worldPortal");

        registerTileEntity(TileEntityWorldPortal.class, "WorldPortals_WorldPortal");

        WorldPortals.channel = ChannelHandler.getChannelHandlers("WorldPortals", PacketWorldPortalInfo.class, PacketRequestChannels.class, PacketChannelList.class);
    }

    public void init()
    {

    }

    public void registerTileEntity(Class<? extends TileEntity> clz, String id)
    {
        GameRegistry.registerTileEntity(clz, id);
    }
}
