package plateau.engine;

import org.lwjgl.opengl.Display;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.input.InputHandler;
import plateau.engine.renderer.FontRenderer;
import plateau.engine.renderer.RenderHandler;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Plateau implements Runnable {
	public static RenderHandler renderHandler;
	public static InputHandler input;
	private DebugTitleInfo debugTitle = new DebugTitleInfo();
	private GameTimer timer = new GameTimer();
	private FontRenderer renderer;
	private PlateauDisplay display;


	public Plateau(PlateauDisplay display) {
		this.display = display;
	}

	public static EntityPlayer getPlayer() {
		return renderHandler.getPlayer();
	}

	public static InputHandler getInput() {
		return input;
	}

	/**
	 * Handles tasks that need to be done in the thread, this will call methods in other classes
	 */
	public void runLoop() {
		//TODO Profiler needed
		if (timer.tick()) input.update();

		renderHandler.update();

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

			if (Display.wasResized()) {
				renderHandler.initCamera(Display.getWidth(), Display.getHeight());
			}

			if (Display.isCloseRequested()) {
				break;
			}
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

	private boolean initThread() {
		display.initThread();
		renderHandler = new RenderHandler();
		input = new InputHandler();

		display.init();
		renderer = new FontRenderer(new Font("Serif", Font.PLAIN, 16), true);

		return true;
	}

	private void set2D() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, display.getWidth(), 0, display.getHeight(), -1, 1);
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
}
