package plateau.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import plateau.engine.input.InputHandler;
import plateau.engine.resource.ResourceLoader;
import plateau.engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;

public abstract class PlateauDisplay implements Runnable {

	public static Scene scene;
	public InputHandler input;
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
			scene = new Scene();
			input = new InputHandler();
			this.init();
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
		input.update();
	}

	@Override
	public void run() {
		if (!initThread()) {
			// Error Message
		}

		while (true) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			runLoop();

			scene.update();
			if (Display.wasResized()) {
				scene.initCamera(Display.getWidth(), Display.getHeight());
			}
			if (Display.isCloseRequested()) {
				break;
			}
			Display.update();
		}
		Display.destroy();
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
			Display.setResizable(true);
			new Thread(this, "LWJGL Display").start();
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
		} else {
			// ERROR
		}
	}

	public abstract void init();
}