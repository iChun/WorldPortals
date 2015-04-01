package us.ichun.mods.worldportals.common;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.ichun.mods.ichunutil.common.core.config.ConfigHandler;
import us.ichun.mods.ichunutil.common.core.network.PacketChannel;
import us.ichun.mods.ichunutil.common.iChunUtil;
import us.ichun.mods.worldportals.common.core.CommonProxy;
import us.ichun.mods.worldportals.common.core.Config;
import us.ichun.mods.worldportals.common.core.EventHandler;

@Mod(modid = "WorldPortals", name = "WorldPortals",
        version = WorldPortals.version,
        dependencies = "required-after:iChunUtil@["+ iChunUtil.versionMC + ".0.0,)"
)
public class WorldPortals
{
    public static final String version = iChunUtil.versionMC + ".0.0";

    private static final Logger logger = LogManager.getLogger("Keygrip");

    @Mod.Instance("WorldPortals")
    public static WorldPortals instance;

    @SidedProxy(clientSide = "us.ichun.mods.worldportals.client.core.ClientProxy", serverSide = "us.ichun.mods.worldportals.common.core.CommonProxy")
    public static CommonProxy proxy;

    public static Config config;

    public static PacketChannel channel;

    public static Block blockWorldPortal;

    public static EventHandler eventHandler;

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {
        config = (Config)ConfigHandler.registerConfig(new Config(event.getSuggestedConfigurationFile()));

        proxy.preInit();

        eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        proxy.init();
    }

    public static void console(String s, boolean warning)
    {
        StringBuilder sb = new StringBuilder();
        logger.log(warning ? Level.WARN : Level.INFO, sb.append("[").append(version).append("] ").append(s).toString());
    }
}
