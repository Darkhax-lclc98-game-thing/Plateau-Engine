package plateau.engine.renderer;

import plateau.engine.PlateauDisplay;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.registery.GameRegistry;
import plateau.engine.renderer.entity.RendererPlayer;
import plateau.engine.world.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class RenderHandler {
	RendererPlayer playerRenderer;
	BlockRenderer blockRenderer = new BlockRenderer();
	private EntityPlayer player;


	public RenderHandler() {
		player = new EntityPlayer();
		playerRenderer = new RendererPlayer(player);
		initCamera(PlateauDisplay.getWidth(), PlateauDisplay.getHeight());
	}

	public void initCamera(int width, int height) {
		glViewport(0, 0, width, height);

		glClearColor(0, 0.75f, 1, 1);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60, (float) width / height, 0.1f, 200);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	public EntityPlayer getPlayer() {
		return player;
	}


	public void update() {
		for (World world : GameRegistry.getWorldList()) {
			blockRenderer.renderWorld(world);
		}

		if (player != null) {
			player.update();
			playerRenderer.update();
		}

	}
}
