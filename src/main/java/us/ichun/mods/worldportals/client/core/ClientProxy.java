package us.ichun.mods.worldportals.client.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import us.ichun.mods.worldportals.client.render.TileRendererWorldPortal;
import us.ichun.mods.worldportals.common.WorldPortals;
import us.ichun.mods.worldportals.common.core.CommonProxy;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init()
    {
        super.init();

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(WorldPortals.blockWorldPortal), 0, new ModelResourceLocation("worldportals:worldPortal", "inventory"));
    }

    @Override
    public void registerTileEntity(Class<? extends TileEntity> clz, String id)
    {
        super.registerTileEntity(clz, id);
        ClientRegistry.bindTileEntitySpecialRenderer(clz, new TileRendererWorldPortal());
    }
}
