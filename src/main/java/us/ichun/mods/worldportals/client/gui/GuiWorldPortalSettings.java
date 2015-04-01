package us.ichun.mods.worldportals.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import us.ichun.mods.ichunutil.client.gui.window.IWorkspace;
import us.ichun.mods.ichunutil.client.gui.window.Window;
import us.ichun.mods.ichunutil.client.gui.window.element.Element;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalCarrier;
import us.ichun.mods.worldportals.client.gui.window.WindowWorldPortalInfo;

import java.util.ArrayList;

public class GuiWorldPortalSettings extends IWorkspace
{
    public int oriScale;

    public WorldPortalCarrier carrier;

    public WindowWorldPortalInfo windowWorldPortal;

    public GuiWorldPortalSettings(WorldPortalCarrier carrier1, int scale)
    {
        VARIABLE_LEVEL = 0;

        carrier = carrier1;

        oriScale = scale;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        levels.clear();

        ArrayList<Window> level = new ArrayList<Window>();

        windowWorldPortal = new WindowWorldPortalInfo(this, 10, 10, width - 20, height - 20, carrier.getPortalInfo());

        level.add(windowWorldPortal);

        levels.add(level);
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);

        Minecraft.getMinecraft().gameSettings.guiScale = oriScale;
    }

    @Override
    public boolean canClickOnElement(Window window, Element element)
    {
        return true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderTick)
    {
        if(mc == null)
        {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, resolution.getScaledWidth_double(), resolution.getScaledHeight_double(), 0.0D, -5000.0D, 5000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();

        GlStateManager.pushMatrix();

        drawDefaultBackground();

        boolean onWindow = drawWindows(mouseX, mouseY);

        int scroll = Mouse.getDWheel();

        updateElementHovered(mouseX, mouseY, scroll);

        GlStateManager.popMatrix();

        updateKeyStates();

        updateWindowDragged(mouseX, mouseY);

        updateElementDragged(mouseX, mouseY);

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, resolution.getScaledWidth_double(), resolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (key == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
        else if(elementSelected != null)
        {
            elementSelected.keyInput(c, key);
        }
    }
}
