package plateau.engine.entity.player;

import static org.lwjgl.opengl.GL11.*;

public class EntityPlayer {
	private double x, y, z;
	private float yaw, pitch, roll;

	private int width, height;

	public EntityPlayer(int width, int height) {

		this.width = width;
		this.height = height;
	}

	public void update() {
		glLoadIdentity();
		glPushAttrib(GL_TRANSFORM_BIT);
		glMatrixMode(GL_MODELVIEW);

		glRotatef(this.getPitch(), 1, 0, 0);
		glRotatef(this.getYaw(), 0, 1, 0);
		glRotatef(this.getRoll(), 0, 0, 1);

		glTranslated(-this.getX(), -this.getY(), -this.getZ());

		glPopAttrib();
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
}
