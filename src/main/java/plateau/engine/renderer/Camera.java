package plateau.engine.renderer;

import org.lwjgl.util.vector.Vector3f;

public class Camera {
	public Vector3f location;

	public int yaw, pitch, roll;

	public int width, height;

	public Camera(int width, int height) {
		location = new Vector3f();

		this.width = width;
		this.height = height;

	}

	public void update(){

	}

}
