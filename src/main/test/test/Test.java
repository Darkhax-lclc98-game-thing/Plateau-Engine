package test;

import plateau.engine.PlateauDisplay;
import plateau.engine.renderer.Camera;
import plateau.engine.shape.Box;

public class Test extends PlateauDisplay {

	public static void main(String[] args) {
		new Test().createWindowDisplay("Test", 800, 400, null);
	}

	@Override
	public void init() {
		new Camera(854, 480);

		Box box = new Box(1, 1, 1);

	}
}
