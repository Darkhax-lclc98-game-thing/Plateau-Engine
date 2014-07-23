package plateau.engine.renderer;

import static org.lwjgl.util.glu.GLU.*;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f location;

	private float yaw, pitch, roll;

	private int width, height;

	public Camera(int width, int height) {
		location = new Vector3f();

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

		glTranslated(-this.getLocation().getX(), -this.getLocation().getY(), -this.getLocation().getZ());

		glPopAttrib();
	}

	public Vector3f getLocation() {
		return this.location;
	}

	public void setLocation(Vector3f vec) {
		this.location = vec;
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

	public float getRoll() {
		return roll;
	}

	public float getPitch() {
		return pitch;
	}

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
