package plateau.engine.world;

import plateau.engine.PlateauDisplay;

import static org.lwjgl.opengl.GL11.*;

public class World {

	public Chunk[][] chunk = new Chunk[1][2];

	public World() {
		PlateauDisplay.scene.registerWorld(this);

		for (int x = 0; x < chunk.length; x++) {
			for (int z = 0; z < chunk.length; z++) {
				chunk[x][z] = new Chunk();
			}
		}
	}

	public void render() {
		for (int x = 0; x < chunk.length; x++) {
			for (int z = 0; z < chunk.length; z++) {
				glPushMatrix();
				glTranslated(x * 2, 0, z);

				chunk[x][z].render();
				glPopMatrix();
			}
		}
	}

}
