package us.ichun.mods.worldportals.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalCarrier;
import us.ichun.mods.worldportals.client.gui.GuiWorldPortalSettings;
import us.ichun.mods.worldportals.common.tileentity.TileEntityWorldPortal;

public class BlockWorldPortal extends Block
    implements ITileEntityProvider
{
    public BlockWorldPortal()
    {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityWorldPortal();
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitVecX, float hitVecY, float hitVecZ)
    {
        if(!world.isRemote)
        {
            openGui(world, pos, state, player, side, hitVecX, hitVecY, hitVecZ);
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void openGui(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitVecX, float hitVecY, float hitVecZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof WorldPortalCarrier)
        {
            Minecraft mc = Minecraft.getMinecraft();
            int oriScale = mc.gameSettings.guiScale;
//            mc.gameSettings.guiScale = mc.gameSettings.guiScale == 1 ? 1 : 2;

            Minecraft.getMinecraft().displayGuiScreen(new GuiWorldPortalSettings((WorldPortalCarrier)te, oriScale));
        }
    }
}
