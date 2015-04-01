package us.ichun.mods.worldportals.client.gui.window;

import net.minecraft.util.StatCollector;
import us.ichun.mods.ichunutil.client.gui.Theme;
import us.ichun.mods.ichunutil.client.gui.window.IWorkspace;
import us.ichun.mods.ichunutil.client.gui.window.Window;
import us.ichun.mods.ichunutil.client.gui.window.element.Element;
import us.ichun.mods.ichunutil.client.gui.window.element.ElementButton;

public class WindowChannelList extends Window
{
    public boolean waitingOnServer = true;

    public WindowChannelList(IWorkspace parent)
    {
        super(parent, 0, 0, 260, 320, 240, 160, "window.linkPortal.title", true);

        elements.add(new ElementButton(this, width / 2 - 30, height - 25, 60, 16, 3, false, 2, 1, "element.button.ok"));
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        //TODO finish up on the packet as well.

        super.draw(mouseX, mouseY);
        if(!minimized)
        {
            workspace.getFontRenderer().drawString(StatCollector.translateToLocal(waitingOnServer ? "window.linkPortal.gettingFromServer" : "window.linkPortal.chooseChannel"), posX + 11, posY + 20, Theme.getAsHex(workspace.currentTheme.font), false);
        }
    }

    @Override
    public void elementTriggered(Element element)
    {
        workspace.removeWindow(this, true);
    }
}
