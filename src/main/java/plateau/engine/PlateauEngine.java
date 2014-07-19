package plateau.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import plateau.engine.resource.ResourceLoader;

import java.io.IOException;

public class PlateauEngine {

    /**
     * The display used for the released game, if it is windowed
     *
     * @param title:  The title used at the top of the display window
     * @param width:  The width of the displayed window
     * @param height: The height of the displayed window
     */
    public void createWindowDisplay(String title, int width, int height, String icon) {
        try {
            Display.setTitle(title);
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setIcon(ResourceLoader.getIcon(icon));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The display used for the released game, if it is fullscreen
     *
     * @param vSync: The Boolean to decided if you want to run the game at a fps around your monitor's refresh rate
     */
    public void createFullScreenDisplay(boolean vSync) {
        try {
            Display.setFullscreen(true);
            Display.setVSyncEnabled(vSync);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

}
