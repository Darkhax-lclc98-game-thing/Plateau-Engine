package plateau.engine.entity.player;


public class EntityPlayer {
	public boolean is3rdPerson;
	public float thirdPersonDistance = 4.0F;
	public float thirdPersonDistanceTemp = 4.0F;


	private double x, y, z;
	private float yaw, pitch, roll;
	private double motionX, motionY, motionZ;

	private int worldID;

	public EntityPlayer() {
		this.setY(18);
		this.setWorldID(0);
	}

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

	public double getMotionZ() {
		return motionZ;
	}

	public void setMotionZ(double motionZ) {
		this.motionZ = motionZ;
	}

	public double getMotionX() {
		return motionX;
	}

	public void setMotionX(double motionX) {
		this.motionX = motionX;
	}

	public double getMotionY() {
		return motionY;
	}

	public void setMotionY(double motionY) {
		this.motionY = motionY;
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}


}
