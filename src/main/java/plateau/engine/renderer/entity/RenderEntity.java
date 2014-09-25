package plateau.engine.renderer.entity;

import plateau.engine.entity.Entity;
import plateau.engine.renderer.model.OBJLoader;

import static org.lwjgl.opengl.GL11.*;

public class RenderEntity extends Render {
	OBJLoader loader = new OBJLoader("blank.obj", true);

	@Override
	public void render(Entity entity, float x, float y, float z) {
		// Renders the blank model just so it can be seen
		glPushMatrix();
		glTranslated(x, y, z);
		loader.render();
		glPopMatrix();
	}

}
