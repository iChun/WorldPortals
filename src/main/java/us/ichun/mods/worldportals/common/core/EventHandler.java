package us.ichun.mods.worldportals.common.core;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalLocation;
import us.ichun.mods.worldportals.common.world.WorldPortalsSaveData;

import java.util.HashMap;

public class EventHandler
{
    public HashMap<World, WorldPortalsSaveData> worldSaveDataMap = new HashMap<World, WorldPortalsSaveData>();

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if(!event.world.isRemote)
        {
            WorldServer world = (WorldServer)event.world;
            WorldPortalsSaveData saveData = (WorldPortalsSaveData)world.getPerWorldStorage().loadData(WorldPortalsSaveData.class, "WorldPortalsSaveData");

            if(saveData == null)
            {
                saveData = new WorldPortalsSaveData(world, "WorldPortalsSaveData");
                world.getPerWorldStorage().setData("WorldPortalsSaveData", saveData);
                saveData.markDirty();
            }
            else
            {
                saveData.attachedWorld = world;
            }

            worldSaveDataMap.put(world, saveData);
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event)
    {
        worldSaveDataMap.remove(event.world);
    }

    public void addWorldPortalToMap(World world, WorldPortalLocation loc)
    {
        if(world.isRemote)
        {
            return;
        }

        WorldPortalsSaveData saveData = worldSaveDataMap.get(world);
        if(saveData != null)
        {
            saveData.worldPortalLocations.add(loc);
            saveData.markDirty();
        }
    }

    public void removeWorldPortalFromMap(World world, WorldPortalLocation loc)
    {
        if(world.isRemote)
        {
            return;
        }

        WorldPortalsSaveData saveData = worldSaveDataMap.get(world);
        if(saveData != null)
        {
            saveData.worldPortalLocations.remove(loc);
            saveData.markDirty();
        }
    }
}
