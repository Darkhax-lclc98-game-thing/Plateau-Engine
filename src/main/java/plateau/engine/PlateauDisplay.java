package plateau.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import plateau.engine.resource.ResourceLoader;

public abstract class PlateauDisplay implements Runnable {

	private boolean isCreated;

	private String title, icon;
	private int width, height;
	private boolean vSync, fullscreen;

	//TODO Error Messages
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

	/**
	 * Handles tasks that need to be done in the thread, this will call methods in other classes
	 */
	public void runLoop() {
		//TODO Profiler needed

	}

	@Override
	public void run() {
		if (!initThread()) {
			// Error Message
		}

		while (true) {

			if (Display.isCloseRequested()) {
				break;
			}
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
		if (!this.isCreated) {
			this.title = title;
			this.width = width;
			this.height = height;
			this.icon = icon;
			this.isCreated = true;

			new Thread(this, "LWJGL Display").start();
			this.init();
		} else {
			// ERROR
		}
	}

	/**
	 * The display used for the released game, if it is fullscreen
	 *
	 * @param vSync: The Boolean to decided if you want to run the game at a fps around your monitor's refresh rate
	 */
	public void createFullScreenDisplay(boolean vSync, String icon) {
		if (!this.isCreated) {
			this.icon = icon;
			this.vSync = vSync;
			this.fullscreen = true;
			this.isCreated = true;

			new Thread(this, "LWJGL Display").start();
			this.init();
		} else {
			// ERROR
		}
	}

	public abstract void init();
}
