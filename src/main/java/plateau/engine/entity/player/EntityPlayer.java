package plateau.engine.entity.player;


import plateau.engine.entity.Entity;
import plateau.engine.registery.GameRegistry;

public class EntityPlayer extends Entity {
	public boolean is3rdPerson;
	public float thirdPersonDistance = 8.0F;

	public EntityPlayer() {
		this.setX(1);
		this.setY(250);
		this.setZ(1);
		this.setWorld(GameRegistry.getWorldList().get(0));
	}
}
