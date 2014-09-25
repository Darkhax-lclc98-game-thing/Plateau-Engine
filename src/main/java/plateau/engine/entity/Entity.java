package plateau.engine.entity;

import plateau.engine.world.World;

public class Entity {

	private float x, y, z;
	private float yaw, pitch, roll;
	private float motionX, motionY, motionZ;

	private World world;

	public void update() {
		this.setX(this.getX() + this.getMotionX());
		this.setY(this.getY() + this.getMotionY());
		this.setZ(this.getZ() + this.getMotionZ());

		if (this.getMotionX() != 0) {
			if (this.getMotionX() < 0.00001 && this.getMotionX() > -0.00001) {
				this.setMotionX(0);
			} else {
				this.setMotionX(this.getMotionX() * 0.5f);
			}
		}

		if (this.getMotionZ() != 0) {
			if (this.getMotionZ() < 0.00001 && this.getMotionZ() > -0.00001) {
				this.setMotionZ(0);
			} else {
				this.setMotionZ(this.getMotionZ() * 0.5f);
			}
		}
	}

	public float getX() {
		return this.x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return this.z;
	}

	public void setZ(float z) {
		this.z = z;
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

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getMotionZ() {
		return motionZ;
	}

	public void setMotionZ(float motionZ) {
		this.motionZ = motionZ;
	}

	public float getMotionX() {
		return motionX;
	}

	public void setMotionX(float motionX) {
		this.motionX = motionX;
	}

	public float getMotionY() {
		return motionY;
	}

	public void setMotionY(float motionY) {
		this.motionY = motionY;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
