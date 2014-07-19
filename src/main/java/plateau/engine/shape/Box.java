package plateau.engine.shape;

import org.lwjgl.util.vector.Vector3f;

public class Box {
	public Vector3f location;

	public Box(float x, float y, float z) {
		location = new Vector3f(x, y, z);
	}
}
