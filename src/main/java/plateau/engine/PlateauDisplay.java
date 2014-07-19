package plateau.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import plateau.engine.resource.ResourceLoader;

public class PlateauDisplay implements Runnable {


	private String title, icon;
	private int width, height;
	private boolean vSync, fullscreen;

	private boolean initThread() {
		try {
			if (fullscreen) {
				Display.setFullscreen(true);
				Display.setVSyncEnabled(vSync);
			} else {
				Display.setTitle(title);
				Display.setDisplayMode(new DisplayMode(width, height));
			}

			if (icon != null && icon != "") {
				Display.setIcon(ResourceLoader.getIcon(icon));
			}

			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void run() {
		if (!initThread()) {
			// Error Message
		}

		while (true) {

			Display.update();
		}
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

		new Thread(this, "LWJGL Display").start();
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
		new Thread(this, "LWJGL Display").start();
	}
}
