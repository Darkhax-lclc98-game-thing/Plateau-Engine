package plateau.game.level;

import plateau.engine.renderer.model.OBJLoader;
import plateau.engine.resource.ResourceLoader;
import plateau.engine.world.IWorld;

import static org.lwjgl.opengl.GL11.*;

public class Level1 extends IWorld {

	OBJLoader loader = new OBJLoader("house.obj", false);

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
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		glTranslated(10, 100, 10);
		glScaled(0.1f, 0.1f, 0.1f);

		ResourceLoader.bindTextures("surface.bmp");
		loader.render();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();

		// calls the heightmap to be rendered
		super.render();
	}
}
