package plateau.engine.scene;

import plateau.engine.renderer.Camera;
import plateau.engine.shape.Shape;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Scene {
	private Camera camera;
	private ArrayList<Shape> objectArray = new ArrayList<Shape>();

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

	public void addObject(Shape shape) {
		objectArray.add(shape);
	}

	public void update() {
		for (Shape shape : objectArray) {
			glTranslated(shape.getLocation().getX() / 2, shape.getLocation().getY() / 2, shape.getLocation().getZ() / 2);
			shape.render();
		}

		if (camera != null) {
			camera.update();
		}
	}
}
