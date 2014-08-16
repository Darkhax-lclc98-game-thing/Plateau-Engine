package plateau.engine.scene;

import plateau.engine.renderer.BlockRenderer;
import plateau.engine.renderer.Camera;
import plateau.engine.world.World;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Scene {
	BlockRenderer blockRenderer = new BlockRenderer();
	private Camera camera;
	private ArrayList<World> worldList = new ArrayList<World>();

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
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
		initCamera(camera.getWidth(), camera.getHeight());
	}

	public void registerWorld(World world) {
		if (!worldList.contains(world)) {
			worldList.add(world);
		}
	}

	public void update() {
		for (World world : worldList) {
			blockRenderer.renderWorld(world);
		}

		if (camera != null) {
			camera.update();

		}


	}
}
