package plateau.game;

import plateau.engine.world.IWorld;

public class GameWorld extends IWorld {
	@Override
	public int getWorldID() {
		return 0;
	}

	@Override
	public String heightmap() {
		return "surface.bmp";
	}

	@Override
	public String texture() {
		return "texture.png";
	}
}
