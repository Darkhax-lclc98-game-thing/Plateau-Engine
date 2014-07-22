package test;

import plateau.engine.PlateauDisplay;
import plateau.engine.renderer.Camera;
import plateau.engine.shape.Box;

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

		Box box = new Box(1, 1, 1);
		scene.addObject(box);

		input.registerInput(new TestInput());

	}

}