package plateau.engine.renderer;

import plateau.engine.PlateauDisplay;
import plateau.engine.block.Block;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.renderer.mc.Tessellator;
import plateau.engine.resource.ResourceLoader;
import plateau.engine.world.World;

import static org.lwjgl.opengl.GL11.*;

public class BlockRenderer {

	private int renderDistance = 32;
	private Tessellator tessellator = Tessellator.instance;

	public void renderWorld(World world) {
		int l = (int) getPlayer().getX();
		int i1 = (int) getPlayer().getY();
		int j1 = (int) getPlayer().getZ();
		int render = GL_QUADS;

		glPushMatrix();
		glEnable(GL_TEXTURE_2D);

		tessellator.startDrawing(render);
		for (int x = l - renderDistance; x < l + renderDistance; x++) {
			for (int y = i1 - 16; y < i1 + 16; y++) {
				for (int z = j1 - renderDistance; z < j1 + renderDistance; z++) {
					setupDraw(world, x, y, z);
				}
			}
		}
		tessellator.draw();

		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public void setupDraw(World world, int x, int y, int z) {
		float size = 1f;

		Block block = world.getBlock(x, y, z);
		ResourceLoader.bindTextures("stone.png");
		// Front 0
		if (world.isAirBlock(x + 1, y, z) &&block.isSideSolid(0) &&  !world.getBlock(x + 1, y, z).isSideSolid(1)) {
			tessellator.addVertexWithUV(x + size, y, z, 1, 1);
			tessellator.addVertexWithUV(x + size, y + size, z, 1, 0);
			tessellator.addVertexWithUV(x + size, y + size, z + size, 0, 0);
			tessellator.addVertexWithUV(x + size, y, z + size, 0, 1);
		}

		// Back 1
		if (world.isAirBlock(x - 1, y, z) && block.isSideSolid(1) && !world.getBlock(x - 1, y, z).isSideSolid(0)) {
			tessellator.addVertexWithUV(x, y + size, z, 0, 0);
			tessellator.addVertexWithUV(x, y, z, 0, 1);
			tessellator.addVertexWithUV(x, y, z + size, 1, 1);
			tessellator.addVertexWithUV(x, y + size, z + size, 1, 0);
		}

		// Right 2
		if (world.isAirBlock(x, y, z + 1) && block.isSideSolid(2) && !world.getBlock(x, y, z + 1).isSideSolid(3)) {
			tessellator.addVertexWithUV(x + size, y, z + size, 1, 1);
			tessellator.addVertexWithUV(x + size, y + size, z + size, 1, 0);
			tessellator.addVertexWithUV(x, y + size, z + size, 0, 0);
			tessellator.addVertexWithUV(x, y, z + size, 0, 1);
		}

		// Left 3
		if (world.isAirBlock(x, y, z - 1) && block.isSideSolid(3) && !world.getBlock(x, y, z - 1).isSideSolid(2)) {
			tessellator.addVertexWithUV(x + size, y, z, 0, 1);
			tessellator.addVertexWithUV(x + size, y + size, z, 0, 0);
			tessellator.addVertexWithUV(x, y + size, z, 1, 0);
			tessellator.addVertexWithUV(x, y, z, 1, 1);
		}

		// Top 4
		if (world.isAirBlock(x, y + 1, z) && block.isSideSolid(4) && !world.getBlock(x, y + 1, z).isSideSolid(5)) {
			tessellator.addVertexWithUV(x + size, y + size, z + size, 0, 0);
			tessellator.addVertexWithUV(x + size, y + size, z, 1, 0);
			tessellator.addVertexWithUV(x, y + size, z, 1, 1);
			tessellator.addVertexWithUV(x, y + size, z + size, 0, 1);
		}

		// Bottom 5
		if (getPlayer().getY() < 0 && world.isAirBlock(x, y - 1, z) && block.isSideSolid(5) && !world.getBlock(x, y - 1, z).isSideSolid(4)) {
			tessellator.addVertexWithUV(x + size, y, z, 0, 0);
			tessellator.addVertexWithUV(x + size, y, z + size, 1, 0);
			tessellator.addVertexWithUV(x, y, z + size, 1, 1);
			tessellator.addVertexWithUV(x, y, z, 0, 1);
		}
	}

	public EntityPlayer getPlayer() {
		return PlateauDisplay.scene.getPlayer();
	}
}
