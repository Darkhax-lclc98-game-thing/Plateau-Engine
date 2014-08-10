package plateau.engine.world;

import plateau.engine.block.Block;
import plateau.engine.shape.Box;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;

public class Chunk {

	private Block[][][] blocks = new Block[16][256][16];

	public Chunk() {
		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks.length; y++) {
				for (int z = 0; z < blocks.length; z++) {
					blocks[x][y][z] = new Block();
				}
			}
		}
	}

	public void render() {
		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks.length; y++) {
				for (int z = 0; z < blocks.length; z++) {
					glPushMatrix();
					glTranslated(x, y, z);
					new Box(0.5f, 0.5f, 0.5f).render();
					glPopMatrix();
				}
			}
		}
	}
}
