package plateau.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import plateau.engine.input.InputHandler;
import plateau.engine.renderer.FontRenderer;
import plateau.engine.resource.ResourceLoader;
import plateau.engine.scene.Scene;
import plateau.engine.util.Logger;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public abstract class PlateauDisplay implements Runnable {

	public static Scene scene;
	private static int width;
	private static int height;
	public InputHandler input;
	FontRenderer renderer;
	private String title, icon;
	private boolean vSync, fullscreen;
	private DebugTitleInfo debugTitle = new DebugTitleInfo();
	private GameTimer timer = new GameTimer();

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	private boolean initThread() {
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
			scene = new Scene();
			input = new InputHandler();

			this.init();
			renderer = new FontRenderer(new Font("Serif", Font.PLAIN, 16), true);
		} catch (LWJGLException e) {
			Logger.log(e.getMessage(), Logger.LogLevel.FATAL);
		}
		return true;
	}

	private void set2D() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, width, 0, height, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
	}

	public void set3D() {
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
		glDisable(GL_BLEND);
	}

	/**
	 * Handles tasks that need to be done in the thread, this will call methods in other classes
	 */
	public void runLoop() {
		//TODO Profiler needed
		if (timer.tick())
			input.update();

		set2D();

		debugTitle.updateDebugTitle(renderer);

		set3D();
	}

	@Override
	public void run() {
		initThread();

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
		this.title = title;
		this.width = width;
		this.height = height;
		this.icon = icon;
		Display.setResizable(true);

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

	public abstract void init();
}