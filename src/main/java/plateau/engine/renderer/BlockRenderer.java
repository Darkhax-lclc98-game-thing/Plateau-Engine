package plateau.engine.renderer;

import plateau.engine.block.Block;
import plateau.engine.renderer.mc.Tessellator;
import plateau.engine.world.World;

import static org.lwjgl.opengl.GL11.*;

public class BlockRenderer {

	int renderDistance = 16;

	public void renderWorld(World world) {
		int l = 0;
		int i1 = 0;
		int j1 = 0;

		glPushMatrix();

		for (int x = l; x < l + renderDistance; x++) {
			for (int y = i1; y < i1 + renderDistance; y++) {
				for (int z = j1; z < j1 + renderDistance; z++) {
					setupDraw(world, x, y, z);
				}
			}
		}

		glPopMatrix();
	}

	public void setupDraw(World world, int x1, int y1, int z1) {

		glPushMatrix();

		float x = 0.25f;
		float y = 0.25f;
		float z = 0.25f;
		int render = GL_QUADS;

		glEnable(GL_TEXTURE_2D);

		RenderEngine.bindTextures("stone.png");
		Block block = world.getBlock(x1, y1, z1);
		glTranslated(x1 * (x * 2), y1 * (y * 2), z1 * (z * 2));
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(render);

		// Front 0
		if (block.isSideSolid(0) && world.getBlock(x1 + 1, y1, z1) != null && !world.getBlock(x1 + 1, y1, z1).isSideSolid(1)) {
			block.getIcon(0);

			tessellator.addVertexWithUV(x, -y, -z, 1, 1);
			tessellator.addVertexWithUV(x, y, -z, 1, 0);
			tessellator.addVertexWithUV(x, y, z, 0, 0);
			tessellator.addVertexWithUV(x, -y, z, 0, 1);
		}

		// Back 1
		if (block.isSideSolid(1) && world.getBlock(x1 - 1, y1, z1) != null && !world.getBlock(x1 - 1, y1, z1).isSideSolid(0)) {
			tessellator.addVertexWithUV(-x, y, -z, 0, 0);
			tessellator.addVertexWithUV(-x, -y, -z, 0, 1);
			tessellator.addVertexWithUV(-x, -y, z, 1, 1);
			tessellator.addVertexWithUV(-x, y, z, 1, 0);
		}

		// Right 2
		if (block.isSideSolid(2) && world.getBlock(x1, y1, z1 + 1) != null && !world.getBlock(x1, y1, z1 + 1).isSideSolid(3)) {
			tessellator.addVertexWithUV(x, -y, z, 1, 1);
			tessellator.addVertexWithUV(x, y, z, 1, 0);
			tessellator.addVertexWithUV(-x, y, z, 0, 0);
			tessellator.addVertexWithUV(-x, -y, z, 0, 1);
		}

		// Left 3
		if (block.isSideSolid(3) && world.getBlock(x1, y1, z1 - 1) != null && !world.getBlock(x1, y1, z1 - 1).isSideSolid(2)) {
			tessellator.addVertexWithUV(x, -y, -z, 0, 1);
			tessellator.addVertexWithUV(x, y, -z, 0, 0);
			tessellator.addVertexWithUV(-x, y, -z, 1, 0);
			tessellator.addVertexWithUV(-x, -y, -z, 1, 1);
		}

		// TODO Fix These
		// Top 4
		if (block.isSideSolid(4) && world.getBlock(x1, y1 + 1, z1) != null && !world.getBlock(x1, y1 + 1, z1).isSideSolid(5)) {
			tessellator.addVertexWithUV(x, y, z, 0, 0);
			tessellator.addVertexWithUV(x, y, -z, 1, 1);
			tessellator.addVertexWithUV(-x, y, -z, 1, 0);
			tessellator.addVertexWithUV(-x, y, z, 0, 1);
		}

		// Bottom 5
		if (block.isSideSolid(5) && world.getBlock(x1, y1 - 1, z1) != null && !world.getBlock(x1, y1 - 1, z1).isSideSolid(4)) {
			tessellator.addVertexWithUV(x, -y, -z, 0,0);
			tessellator.addVertexWithUV(x, -y, z, 1, 1);
			tessellator.addVertexWithUV(-x, -y, z, 1, 0);
			tessellator.addVertexWithUV(-x, -y, -z, 0, 1);
		}
		tessellator.draw();

		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
}
