package plateau.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import plateau.engine.PlateauDisplay;
import plateau.engine.security.PlateauSecurityManager;
import plateau.engine.world.World;

public class Plateau extends PlateauDisplay {

	private static int width = 854;
	private static int height = 480;

	public static void main(String[] args) {
		new Plateau().createWindowDisplay("Test", width, height, null);
	}

	@Override
	public void init() {
		Mouse.setGrabbed(true);
		Keyboard.enableRepeatEvents(true);
		input.registerInput(new PlateauKeyBoard());
		input.registerInput(new PlateauMouse());

		new World();
		
		System.setSecurityManager(new PlateauSecurityManager());
	}

	@Override
	public void runLoop() {
		super.runLoop();
	}

	public static void shutdown() {
		Display.destroy();
		System.exit(0);
	}
}