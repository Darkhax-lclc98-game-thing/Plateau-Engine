package plateau.engine.renderer;

import plateau.engine.block.Block;
import plateau.engine.renderer.mc.Tessellator;
import plateau.engine.resource.ResourceLoader;
import plateau.engine.world.World;

import static org.lwjgl.opengl.GL11.*;

public class BlockRenderer {

	int renderDistance = 16;

	public void renderWorld(World world) {
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int render = GL_QUADS;
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(render);
		for (int x = l; x < l + renderDistance; x++) {
			for (int y = i1; y < i1 + renderDistance; y++) {
				for (int z = j1; z < j1 + renderDistance; z++) {
					setupDraw(world, x, y, z);
				}
			}
		}
		tessellator.draw();

		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public void setupDraw(World world, int x, int y, int z) {

		glPushMatrix();

		float size = 1f;



		ResourceLoader.bindTextures("stone.png");
		Block block = world.getBlock(x, y, z);

		Tessellator tessellator = Tessellator.instance;
		// Front 0
		if (block.isSideSolid(0) && world.getBlock(x + 1, y, z) != null && !world.getBlock(x + 1, y, z).isSideSolid(1)) {
			block.getIcon(0);

			tessellator.addVertexWithUV(x + size, y, z, 1, 1);
			tessellator.addVertexWithUV(x + size, y + size, z, 1, 0);
			tessellator.addVertexWithUV(x + size, y + size, z + size, 0, 0);
			tessellator.addVertexWithUV(x + size, y, z + size, 0, 1);
		}

		// Back 1
		if (block.isSideSolid(1) && world.getBlock(x - 1, y, z) != null && !world.getBlock(x - 1, y, z).isSideSolid(0)) {
			tessellator.addVertexWithUV(x, y + size, z, 0, 0);
			tessellator.addVertexWithUV(x, y, z, 0, 1);
			tessellator.addVertexWithUV(x, y, z + size, 1, 1);
			tessellator.addVertexWithUV(x, y + size, z + size, 1, 0);
		}

		// Right 2
		if (block.isSideSolid(2) && world.getBlock(x, y, z + 1) != null && !world.getBlock(x, y, z + 1).isSideSolid(3)) {
			tessellator.addVertexWithUV(x + size, y, z + size, 1, 1);
			tessellator.addVertexWithUV(x + size, y + size, z + size, 1, 0);
			tessellator.addVertexWithUV(x, y + size, z + size, 0, 0);
			tessellator.addVertexWithUV(x, y, z + size, 0, 1);
		}

		// Left 3
		if (block.isSideSolid(3) && world.getBlock(x, y, z - 1) != null && !world.getBlock(x, y, z - 1).isSideSolid(2)) {
			tessellator.addVertexWithUV(x + size, y, z, 0, 1);
			tessellator.addVertexWithUV(x + size, y + size, z, 0, 0);
			tessellator.addVertexWithUV(x, y + size, z, 1, 0);
			tessellator.addVertexWithUV(x, y, z, 1, 1);
		}

		// TODO Fix these
		// Top 4
		if (block.isSideSolid(4) && world.getBlock(x, y + 1, z) != null && !world.getBlock(x, y + 1, z).isSideSolid(5)) {
			tessellator.addVertexWithUV(x + size, y + size, z + size, 0, 0);
			tessellator.addVertexWithUV(x + size, y + size, z, 1, 1);
			tessellator.addVertexWithUV(x, y + size, z, 1, 0);
			tessellator.addVertexWithUV(x, y + size, z + size, 0, 1);
		}

		// Bottom 5
		if (block.isSideSolid(5) && world.getBlock(x, y - 1, z) != null && !world.getBlock(x, y - 1, z).isSideSolid(4)) {
			tessellator.addVertexWithUV(x + size, y, z, 0, 0);
			tessellator.addVertexWithUV(x + size, y, z + size, 1, 1);
			tessellator.addVertexWithUV(x, y, z + size, 1, 0);
			tessellator.addVertexWithUV(x, y, z, 0, 1);
		}

		glPopMatrix();
	}
}
