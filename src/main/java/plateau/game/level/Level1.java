package plateau.game.level;

import plateau.engine.object.ObjectBuilding;
import plateau.engine.renderer.model.OBJLoader;
import plateau.engine.world.World;
import plateau.game.entity.EntityMother;

public class Level1 extends World {

	public Level1() {
		ObjectBuilding building = new ObjectBuilding(new OBJLoader("house.obj", true));
		building.setX(1000);
		building.setY(225);
		building.setZ(370);

		building.setSizeX(0.1f);
		building.setSizeY(0.1f);
		building.setSizeZ(0.1f);
		building.setRoll(0.1f);
		buildingList.add(building);

        entityList.add(new EntityMother());
	}

	@Override
	public int getChunkSize() {
		return 128;
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
	public void update() {
		// Calls the objects that need to be rendered on the map
		for (ObjectBuilding building : buildingList) {
			building.render();
		}

		// calls the heightmap to be rendered
		super.update();
	}
}
