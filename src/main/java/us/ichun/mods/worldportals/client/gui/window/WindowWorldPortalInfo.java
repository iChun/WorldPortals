package us.ichun.mods.worldportals.client.gui.window;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import us.ichun.mods.ichunutil.client.gui.Theme;
import us.ichun.mods.ichunutil.client.gui.window.Window;
import us.ichun.mods.ichunutil.client.gui.window.element.*;
import us.ichun.mods.ichunutil.common.module.worldportals.WorldPortalInfo;
import us.ichun.mods.worldportals.client.gui.GuiWorldPortalSettings;
import us.ichun.mods.worldportals.common.WorldPortals;
import us.ichun.mods.worldportals.common.packet.PacketWorldPortalInfo;

public class WindowWorldPortalInfo extends Window
{
    public GuiWorldPortalSettings parent;

    public WorldPortalInfo worldPortal;

    public static final int ID_ORIENTATION = 0;
    public static final int ID_ORIENTATION_PLAYER = 1;
    public static final int ID_FACE = 2;
    public static final int ID_HORIZONTAL = 3;
    public static final int ID_HEIGHT = 4;
    public static final int ID_WIDTH = 5;
    public static final int ID_OFFSET_HEIGHT = 6;
    public static final int ID_OFFSET_DEPTH = 7;
    public static final int ID_ACTIVE = 8;
    public static final int ID_PROJECT = 9;

    public WindowWorldPortalInfo(GuiWorldPortalSettings parent, int x, int y, int w, int h, WorldPortalInfo info)
    {
        super(parent, x, y, w, h, 400, 400, "window.worldPortalInfo.title", true);

        this.parent = parent;

        this.worldPortal = info;

        elements.add(new ElementButton(this, width / 2 - 30, height - 22, 60, 16, -200, false, 2, 1, "gui.done"));

        int width = 80;

        int row1 = 30;
        int count = 0;

        elements.add(new ElementNumberInput(this, 10 + ((width + 2) * count++), row1, width, 12, ID_ORIENTATION, "window.worldPortalInfo.orientation", 1, false, 0, 3, worldPortal.orientation));
        elements.add(new ElementButtonTooltip(this, 10 + ((width + 2) * count++), row1, width, 12, ID_ORIENTATION_PLAYER, false, 0, 0 , "window.worldPortalInfo.orientToPlayerShort", "window.worldPortalInfo.orientation"));
        elements.add(new ElementNumberInput(this, 10 + ((width + 2) * count++), row1, width, 12, ID_FACE, "window.worldPortalInfo.face", 1, false, 0, 5, worldPortal.face));
        elements.add(new ElementToggle(this, 10 + ((width + 2) * count++), row1, width, 12, ID_HORIZONTAL, false, 0, 0, "window.worldPortalInfo.horizontalBtn", "window.worldPortalInfo.horizontal", worldPortal.horizontal));

        int row2 = 60;
        count = 0;
        elements.add(new ElementNumberInput(this, 10 + ((width + 2) * count++), row2, width, 12, ID_HEIGHT, "window.worldPortalInfo.height", 1, true, 0.1D, 20D, worldPortal.height));
        elements.add(new ElementNumberInput(this, 10 + ((width + 2) * count++), row2, width, 12, ID_WIDTH, "window.worldPortalInfo.width", 1, true, 0.05D, 10D, worldPortal.width));
        elements.add(new ElementNumberInput(this, 10 + ((width + 2) * count++), row2, width, 12, ID_OFFSET_HEIGHT, "window.worldPortalInfo.offsetHeight", 1, true, 0D, 10D, worldPortal.offsetHeight));
        elements.add(new ElementNumberInput(this, 10 + ((width + 2) * count++), row2, width, 12, ID_OFFSET_DEPTH, "window.worldPortalInfo.offsetDepth", 1, true, 0D, 10D, worldPortal.offsetDepth));

        int row3 = 90;
        count = 0;
        elements.add(new ElementToggle(this, 10 + ((width + 2) * count++), row3, width, 12, ID_ACTIVE, false, 0, 0, "window.worldPortalInfo.activeBtn", "window.worldPortalInfo.active", worldPortal.active));
        elements.add(new ElementToggle(this, 10 + ((width + 2) * count++), row3, width, 12, ID_PROJECT, false, 0, 0, "window.worldPortalInfo.projectBtn", "window.worldPortalInfo.project", worldPortal.project));
    }

    @Override
    public void elementTriggered(Element element)
    {
        if(element.id == ID_ORIENTATION_PLAYER)
        {
            for(Element e : elements)
            {
                if(e.id == ID_ORIENTATION)
                {
                    ((ElementNumberInput)e).textFields.get(0).setText(Integer.toString(((4 - MathHelper.floor_double((double)(Minecraft.getMinecraft().thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4));
                    break;
                }
            }
        }
        if(element.id == -200)
        {
            WorldPortalInfo worldPortalClone = new WorldPortalInfo();

            NBTTagCompound tag = new NBTTagCompound();
            worldPortal.write(tag);
            worldPortalClone.read(tag);

            for(Element e : elements)
            {
                if(e instanceof ElementNumberInput)
                {
                    ElementNumberInput num = (ElementNumberInput)e;
                    if(e.id == ID_ORIENTATION)
                    {
                        worldPortalClone.orientation = Integer.parseInt(num.textFields.get(0).getText());
                    }
                    else if(e.id == ID_FACE)
                    {
                        worldPortalClone.face = Integer.parseInt(num.textFields.get(0).getText());
                    }
                    else if(e.id == ID_HEIGHT)
                    {
                        worldPortalClone.height = Double.parseDouble(num.textFields.get(0).getText());
                    }
                    else if(e.id == ID_WIDTH)
                    {
                        worldPortalClone.width = Double.parseDouble(num.textFields.get(0).getText());
                    }
                    else if(e.id == ID_OFFSET_HEIGHT)
                    {
                        worldPortalClone.offsetHeight = Double.parseDouble(num.textFields.get(0).getText());
                    }
                    else if(e.id == ID_OFFSET_DEPTH)
                    {
                        worldPortalClone.offsetDepth = Double.parseDouble(num.textFields.get(0).getText());
                    }
                }
                else if(e instanceof ElementToggle)
                {
                    ElementToggle toggle = (ElementToggle)e;
                    if(e.id == ID_HORIZONTAL)
                    {
                        worldPortalClone.horizontal = toggle.toggledState;
                    }
                    else if(e.id == ID_ACTIVE)
                    {
                        worldPortalClone.active = toggle.toggledState;
                    }
                    else if(e.id == ID_PROJECT)
                    {
                        worldPortalClone.project = toggle.toggledState;
                    }
                }
            }

            WorldPortals.channel.sendToServer(new PacketWorldPortalInfo(parent.carrier.getPos(), worldPortalClone));

            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        super.draw(mouseX, mouseY);
        int width = 80;

        int row1 = 20;
        int count = 0;

        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.orientation"), posX + (11 + ((width + 2) * count++)), posY + row1, Theme.getAsHex(workspace.currentTheme.font), false);
        count++;
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.face"), posX + (11 + ((width + 2) * count++)), posY + row1, Theme.getAsHex(workspace.currentTheme.font), false);
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.horizontalShort"), posX + (11 + ((width + 2) * count++)), posY + row1, Theme.getAsHex(workspace.currentTheme.font), false);

        int row2 = 50;
        count = 0;
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.height"), posX + (11 + ((width + 2) * count++)), posY + row2, Theme.getAsHex(workspace.currentTheme.font), false);
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.width"), posX + (11 + ((width + 2) * count++)), posY + row2, Theme.getAsHex(workspace.currentTheme.font), false);
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.offsetHeight"), posX + (11 + ((width + 2) * count++)), posY + row2, Theme.getAsHex(workspace.currentTheme.font), false);
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.offsetDepth"), posX + (11 + ((width + 2) * count++)), posY + row2, Theme.getAsHex(workspace.currentTheme.font), false);

        int row3 = 80;
        count = 0;
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.active"), posX + (11 + ((width + 2) * count++)), posY + row3, Theme.getAsHex(workspace.currentTheme.font), false);
        workspace.getFontRenderer().drawString(StatCollector.translateToLocal("window.worldPortalInfo.projectBtn"), posX + (11 + ((width + 2) * count++)), posY + row3, Theme.getAsHex(workspace.currentTheme.font), false);
    }

    @Override
    public void resized()
    {
        posX = 10;
        posY = 10;
        width = parent.width - 20;
        height = parent.height - 20;
        super.resized();
    }

    @Override
    public int clickedOnBorder(int mouseX, int mouseY, int id)//only left clicks
    {
        return 0;
    }

    @Override
    public boolean canBeDragged()
    {
        return false;
    }

    @Override
    public boolean canMinimize()
    {
        return false;
    }

    @Override
    public boolean isStatic()
    {
        return true;
    }
}
