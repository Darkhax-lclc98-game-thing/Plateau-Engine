package plateau.engine.shape;

import static org.lwjgl.opengl.GL11.*;

public class Box extends Shape {

	public Box(float sizeX, float sizeY, float sizeZ) {
		super(sizeX, sizeY, sizeZ);
	}

	@Override
	public void render() {

		float x = this.getSize().getX() / 10;
		float y = this.getSize().getY() / 10;
		float z = this.getSize().getZ() / 10;

		glBegin(GL_QUADS);
		glColor3d(1.0, 0.5, 0.0);
		glVertex3d(x, -y, -z);      // P1 is red
		glVertex3d(x, y, -z);      // P2 is green
		glVertex3d(-x, y, -z);      // P3 is blue
		glVertex3d(-x, -y, -z);      // P4 is purple
		glEnd();

		glBegin(GL_QUADS);
		glColor3d(1.0, 1.0, 1.0);
		glVertex3d(x, -y, z);
		glVertex3d(x, y, z);
		glVertex3d(-x, y, z);
		glVertex3d(-x, -y, z);
		glEnd();

		// Purple side - RIGHT
		glBegin(GL_QUADS);
		glColor3d(1.0, 0.0, 1.0);
		glVertex3d(x, -y, -z);
		glVertex3d(x, y, -z);
		glVertex3d(x, y, z);
		glVertex3d(x, -y, z);
		glEnd();

		// Green side - LEFT
		glBegin(GL_QUADS);
		glColor3d(0.0, 1.0, 0.0);
		glVertex3d(-x, -y, z);
		glVertex3d(-x, y, z);
		glVertex3d(-x, y, -z);
		glVertex3d(-x, -y, -z);
		glEnd();

		// Blue side - TOP
		glBegin(GL_QUADS);
		glColor3d(0.0, 0.0, 1.0);
		glVertex3d(x, y, z);
		glVertex3d(x, y, -z);
		glVertex3d(-x, y, -z);
		glVertex3d(-x, y, z);
		glEnd();

		// Red side - BOTTOM
		glBegin(GL_QUADS);
		glColor3d(1.0, 0.0, 0.0);
		glVertex3d(x, -y, -z);
		glVertex3d(x, -y, z);
		glVertex3d(-x, -y, z);
		glVertex3d(-x, -y, -z);
		glEnd();
	}
}
