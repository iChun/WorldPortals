package us.ichun.mods.worldportals.common.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalCarrier;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalLocation;

import java.util.ArrayList;

public class WorldPortalsSaveData extends WorldSavedData
{
    public World attachedWorld;
    public ArrayList<WorldPortalLocation> worldPortalLocations = new ArrayList<WorldPortalLocation>();

    public WorldPortalsSaveData(String name)
    {
        super(name);
    }

    public WorldPortalsSaveData(World world, String name)
    {
        super(name);
        attachedWorld = world;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        //TODO attached world still isn't fucking set when this is read
        int count = tag.getInteger("portalCount");
        for(int i = 0; i < count; i++)
        {
            BlockPos blockPos = new BlockPos(tag.getInteger("portal_" + i + "_X"), tag.getInteger("portal_" + i + "_Y"), tag.getInteger("portal_" + i + "_Z"));
            TileEntity te = attachedWorld.getTileEntity(blockPos);
            if(te instanceof WorldPortalCarrier)
            {
                worldPortalLocations.add(new WorldPortalLocation(((WorldPortalCarrier)te).getPortalInfo(), blockPos));
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setInteger("portalCount", worldPortalLocations.size());
        for(int i = 0; i < worldPortalLocations.size(); i++)
        {
            WorldPortalLocation loc = worldPortalLocations.get(i);
            tag.setInteger("portal_" + i + "_X", loc.pos.getX());
            tag.setInteger("portal_" + i + "_Y", loc.pos.getY());
            tag.setInteger("portal_" + i + "_Z", loc.pos.getZ());
        }
    }
}
