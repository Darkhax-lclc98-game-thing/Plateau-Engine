package test;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import plateau.engine.PlateauDisplay;
import plateau.engine.input.IInput;
import plateau.engine.renderer.Camera;
import plateau.engine.shape.Box;

import static java.lang.Math.*;
import static java.lang.Math.sin;

public class Test extends PlateauDisplay {

	private static int width = 854;
	private static int height = 480;
	private static float speed = 50f;

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

	private static void moveFromLook(Camera cam, float dx, float dz) {
		float x = (float) (cam.getLocation().getX() - dx * (float) sin(toRadians(cam.getYaw() - 90)) + dz * sin(toRadians(-cam.getYaw())));
		float z = (float) (cam.getLocation().getZ() + dx * (float) cos(toRadians(cam.getYaw() - 90)) + dz * cos(toRadians(-cam.getYaw())));
		cam.getLocation().translate(x, cam.getLocation().getY(), z);
	}
}
