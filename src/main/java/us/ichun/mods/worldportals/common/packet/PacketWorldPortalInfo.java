package us.ichun.mods.worldportals.common.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import us.ichun.mods.ichunutil.common.core.network.AbstractPacket;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalCarrier;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalInfo;

import java.io.IOException;

public class PacketWorldPortalInfo extends AbstractPacket
{
    public BlockPos pos;
    public NBTTagCompound portalInfo;

    public PacketWorldPortalInfo(){}

    public PacketWorldPortalInfo(BlockPos pos, WorldPortalInfo info)
    {
        this.pos = pos;
        portalInfo = new NBTTagCompound();
        info.write(portalInfo);
    }

    @Override
    public void writeTo(ByteBuf buffer, Side side)
    {
        PacketBuffer pb = new PacketBuffer(buffer);
        pb.writeBlockPos(pos);
        pb.writeNBTTagCompoundToBuffer(portalInfo);
    }

    @Override
    public void readFrom(ByteBuf buffer, Side side)
    {
        PacketBuffer pb = new PacketBuffer(buffer);
        pos = pb.readBlockPos();
        try
        {
            portalInfo = pb.readNBTTagCompoundFromBuffer();
        }
        catch(IOException e)
        {
            portalInfo = null;
        }
    }

    @Override
    public void execute(Side side, EntityPlayer player)
    {
        if(portalInfo != null)
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            if(te instanceof WorldPortalCarrier)
            {
                ((WorldPortalCarrier)te).getPortalInfo().readSelfInfo(portalInfo);
                te.getWorld().markBlockForUpdate(pos);
            }
        }
    }
}
