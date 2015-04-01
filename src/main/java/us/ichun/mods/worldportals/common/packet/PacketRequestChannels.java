package us.ichun.mods.worldportals.common.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import us.ichun.mods.ichunutil.common.core.network.AbstractPacket;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalCarrier;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalLocation;
import us.ichun.mods.worldportals.common.WorldPortals;

public class PacketRequestChannels extends AbstractPacket
{
    public BlockPos pos;

    public PacketRequestChannels(){}

    public PacketRequestChannels(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void writeTo(ByteBuf buffer, Side side)
    {
        PacketBuffer pb = new PacketBuffer(buffer);
        pb.writeBlockPos(pos);
    }

    @Override
    public void readFrom(ByteBuf buffer, Side side)
    {
        PacketBuffer pb = new PacketBuffer(buffer);
        pos = pb.readBlockPos();
    }

    @Override
    public void execute(Side side, EntityPlayer player)
    {
        TileEntity te = player.worldObj.getTileEntity(pos);
        if(te instanceof WorldPortalCarrier)
        {
            WorldPortalLocation loc = new WorldPortalLocation(((WorldPortalCarrier)te).getPortalInfo(), pos);
            WorldPortals.channel.sendToPlayer(new PacketChannelList(player.worldObj, loc), player);
        }
    }
}
