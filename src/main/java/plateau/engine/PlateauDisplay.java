package plateau.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import plateau.engine.resource.ResourceLoader;
import plateau.engine.util.Logger;

public abstract class PlateauDisplay {

	private static int width, height;
	private String title, icon;
	private boolean vSync, fullscreen;

	private Plateau plat = new Plateau(this);

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}


	/**
	 * The display used for the released game, if it is windowed
	 *
	 * @param title:  The title used at the top of the display window
	 * @param width:  The width of the displayed window
	 * @param height: The height of the displayed window
	 */
	public void createWindowDisplay(String title, int width, int height, String icon) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.icon = icon;
		Display.setResizable(true);

		new Thread(plat, "LWJGL Display").start();
	}

	/**
	 * The display used for the released game, if it is fullscreen
	 *
	 * @param vSync: The Boolean to decided if you want to run the game at a fps around your monitor's refresh rate
	 */
	public void createFullScreenDisplay(boolean vSync, String icon) {
		this.icon = icon;
		this.vSync = vSync;
		this.fullscreen = true;
		new Thread(plat, "LWJGL Display").start();
	}

	public void initThread() {
		try {
			if (fullscreen) {
				Display.setFullscreen(true);
				Display.setVSyncEnabled(vSync);
			} else {
				Display.setTitle(title);
				Display.setDisplayMode(new DisplayMode(width, height));
			}

			if (icon != null && !icon.equals("")) {
				Display.setIcon(ResourceLoader.getIcon(icon));
			}

			Display.create();
		} catch (LWJGLException e) {
			Logger.log(e.getMessage(), Logger.LogLevel.FATAL);
		}
	}

	public abstract void init();

	public void runLoop() {
		plat.runLoop();
	}
}