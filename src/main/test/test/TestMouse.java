package test;

import plateau.engine.input.IMouse;
import plateau.engine.renderer.Camera;

public class TestMouse implements IMouse {

	private static int maxLookUp = 80;
	private static int maxLookDown = -80;

	@Override
	public void onPress(int eventButton) {

	}

	@Override
	public void onMove(int x, int y) {
		Camera cam = Test.scene.getCamera();

		float mouseDX = x * 0.16f;
		float mouseDY = y * 0.16f;
		if (cam.getYaw() + mouseDX >= 360) {
			cam.setYaw(cam.getYaw() + mouseDX - 360);
		} else if (cam.getYaw() + mouseDX < 0) {
			cam.setYaw(360 - cam.getYaw() + mouseDX);
		} else {
			cam.setYaw(cam.getYaw() + mouseDX);
		}
		if (cam.getPitch() - mouseDY >= maxLookDown && cam.getPitch() - mouseDY <= maxLookUp) {
			cam.setPitch(cam.getPitch() + -mouseDY);
		} else if (cam.getPitch() - mouseDY < maxLookDown) {
			cam.setPitch(maxLookDown);
		} else if (cam.getPitch() - mouseDY > maxLookUp) {
			cam.setPitch(maxLookUp);
		}
	}
}
