package us.ichun.mods.worldportals.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import us.ichun.mods.worldportals.common.tileentity.TileEntityWorldPortal;

public class TileRendererWorldPortal extends TileEntitySpecialRenderer
{
    public void renderWorldPortal(TileEntityWorldPortal wp, double x, double y, double z, float f, int destroyState)
    {
        GlStateManager.pushMatrix();

        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);

        wp.portalInfo.render(wp.getWorld(), x, y, z, f);

        GlStateManager.popMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f, int destroyState)
    {
        renderWorldPortal((TileEntityWorldPortal)te, x, y, z, f, destroyState);
    }
}
