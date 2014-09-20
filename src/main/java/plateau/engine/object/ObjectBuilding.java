package plateau.engine.object;

import plateau.engine.renderer.model.OBJLoader;
import plateau.engine.resource.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class ObjectBuilding {
	private OBJLoader loader;

	private float x, y, z;
	private float sizeX, sizeY, sizeZ;
	private float yaw, pitch, roll;

	public ObjectBuilding(OBJLoader loader) {
		this.loader = loader;
	}

	public void render() {
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		ResourceLoader.bindTextures("surface.bmp");

		glTranslated(x, y, z);
		glScaled(sizeX, sizeY, sizeZ);
		glRotatef(yaw, 1.0f, 0.0f, 0.0f);
		glRotatef(pitch, 0.0f, 1.0f, 0.0f);
		glRotatef(roll, 0.0f, 0.0f, 1.0f);


		loader.render();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getSizeX() {
		return sizeX;
	}

	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}

	public float getSizeZ() {
		return sizeZ;
	}

	public void setSizeZ(float sizeZ) {
		this.sizeZ = sizeZ;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

}
