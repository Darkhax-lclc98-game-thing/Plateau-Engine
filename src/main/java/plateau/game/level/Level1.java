package plateau.game.level;

import plateau.engine.object.ObjectBuilding;
import plateau.engine.renderer.model.OBJLoader;
import plateau.engine.resource.ResourceLoader;
import plateau.engine.world.IWorld;

import static org.lwjgl.opengl.GL11.*;

public class Level1 extends IWorld {

	ObjectBuilding building = new ObjectBuilding(new OBJLoader("house.obj", true));

	public Level1() {
		building.setX(10);
		building.setY(100);
		building.setZ(10);

		building.setSizeX(0.1f);
		building.setSizeY(0.1f);
		building.setSizeZ(0.1f);
	}

	@Override
	public int getWorldID() {
		return 0;
	}

	@Override
	public int getChunkSize() {
		return 64;
	}

	@Override
	public String heightmap() {
		return "surface.bmp";
	}

	@Override
	public String texture() {
		return "texture.png";
	}

	@Override
	public void render() {
		// Calls the objects that need to be rendered on the map
		building.render();

		// calls the heightmap to be rendered
		super.render();
	}
}
