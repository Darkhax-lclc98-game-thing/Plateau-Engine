package test;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import plateau.engine.input.IKeyboard;
import plateau.engine.renderer.Camera;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class TestKeyboard implements IKeyboard {

	private static float speed = 0.09f;

	@Override
	public int[] getKey() {
		return new int[]{Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D};
	}

	@Override
	public String[] getName() {
		return new String[]{"Forward", "Back", "Left", "Right"};
	}

	@Override
	public void onPressed(String name, boolean isRepeated, boolean onPressed) {
		if (onPressed) {
			if (name.equalsIgnoreCase("Forward")) {
				move(0, -speed);
			} else if (name.equalsIgnoreCase("Back")) {
				move(0, speed);
			} else if (name.equalsIgnoreCase("Left")) {
				move(-speed, 0);
			} else if (name.equalsIgnoreCase("Right")) {
				move(speed, 0);
			}
		}
	}

	private void move(float dx, float dz) {
		Camera cam = Test.scene.getCamera();
		Vector3f camLoc = cam.getLocation();
		float x = (float) (camLoc.getX() - dx * (float) sin(toRadians(cam.getYaw() - 90)) + dz * sin(toRadians(-cam.getYaw())));
		float z = (float) (camLoc.getZ() + dx * (float) cos(toRadians(cam.getYaw() - 90)) + dz * cos(toRadians(-cam.getYaw())));

		cam.setLocation(new Vector3f(x, camLoc.getY(), z));
	}
}
