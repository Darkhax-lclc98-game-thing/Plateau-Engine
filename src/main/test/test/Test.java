package test;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import plateau.engine.PlateauDisplay;
import plateau.engine.renderer.Camera;
import plateau.engine.world.World;

public class Test extends PlateauDisplay {

	private static int width = 854;
	private static int height = 480;

	public static void main(String[] args) {
		new Test().createWindowDisplay("Test", width, height, null);
	}

	@Override
	public void init() {
		final Camera cam = new Camera(width, height);
		scene.setCamera(cam);

		Mouse.setGrabbed(true);
		Keyboard.enableRepeatEvents(true);
		input.registerInput(new TestKeyboard());
		input.registerInput(new TestMouse());
		new World();
	}

	@Override
	public void runLoop() {
		super.runLoop();
	}
}