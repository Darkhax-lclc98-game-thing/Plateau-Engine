package plateau.engine.shape;

import org.lwjgl.util.vector.Vector3f;

public abstract class Shape {

	private Vector3f size;
	private Vector3f location;

	public Shape(float sizeX, float sizeY, float sizeZ, float x, float y, float z) {
		size = new Vector3f(sizeX, sizeY, sizeZ);
		location = new Vector3f(x, y, z);
	}

	public Shape(float sizeX, float sizeY, float sizeZ) {
		this(sizeX, sizeY, sizeZ, 0, 0, 0);
	}

	public Shape() {
		this(1, 1, 1);
	}

	public Vector3f getLocation() {
		return this.location;
	}

	public void setLocation(Vector3f vec) {
		this.location = vec;
	}

	public Vector3f getSize() {
		return this.size;
	}

	public abstract void render();
}
