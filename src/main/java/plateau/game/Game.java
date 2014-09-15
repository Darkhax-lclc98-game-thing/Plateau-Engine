package plateau.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import plateau.engine.PlateauDisplay;
import plateau.engine.registery.GameRegistry;
import plateau.engine.security.PlateauSecurityManager;

public class Game extends PlateauDisplay {

	private static int width = 854;
	private static int height = 480;

	public static void main(String[] args) {
		new Game().createWindowDisplay("Test", width, height, null);
	}

	public static void shutdown() {
		Display.destroy();
		System.exit(0);
	}

	@Override
	public void init() {
		Mouse.setGrabbed(true);
		Keyboard.enableRepeatEvents(true);
		GameRegistry.registerInput(new GameKeyboard());
		GameRegistry.registerInput(new GameMouse());
		GameRegistry.registerWorld(new GameWorld());
		System.setSecurityManager(new PlateauSecurityManager());
	}

	@Override
	public void runLoop() {
		super.runLoop();
	}
}