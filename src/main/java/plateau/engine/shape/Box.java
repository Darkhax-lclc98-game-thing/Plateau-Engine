package plateau.engine.shape;

import static org.lwjgl.opengl.GL11.*;

public class Box extends Shape {

	public Box(float sizeX, float sizeY, float sizeZ) {
		super(sizeX, sizeY, sizeZ);
	}

	@Override
	public void render() {
		glBegin(GL_TRIANGLES);                    // Drawing Using Triangles
		glColor3f(1.0f, 0.0f, 0.0f);             // Red
		glVertex3f(0.0f, 1.0f, 0.0f);         // Top Of Triangle (Front)
		glColor3f(0.0f, 1.0f, 0.0f);             // Green
		glVertex3f(-1.0f, -1.0f, 1.0f);         // Left Of Triangle (Front)
		glColor3f(0.0f, 0.0f, 1.0f);             // Blue
		glVertex3f(1.0f, -1.0f, 1.0f);         // Right Of Triangle (Front)
		glColor3f(1.0f, 0.0f, 0.0f);             // Red
		glVertex3f(0.0f, 1.0f, 0.0f);         // Top Of Triangle (Right)
		glColor3f(0.0f, 0.0f, 1.0f);             // Blue
		glVertex3f(1.0f, -1.0f, 1.0f);         // Left Of Triangle (Right)
		glColor3f(0.0f, 1.0f, 0.0f);             // Green
		glVertex3f(1.0f, -1.0f, -1.0f);            // Right Of Triangle (Right)
		glColor3f(1.0f, 0.0f, 0.0f);             // Red
		glVertex3f(0.0f, 1.0f, 0.0f);         // Top Of Triangle (Back)
		glColor3f(0.0f, 1.0f, 0.0f);             // Green
		glVertex3f(1.0f, -1.0f, -1.0f);            // Left Of Triangle (Back)
		glColor3f(0.0f, 0.0f, 1.0f);             // Blue
		glVertex3f(-1.0f, -1.0f, -1.0f);            // Right Of Triangle (Back)
		glColor3f(1.0f, 0.0f, 0.0f);             // Red
		glVertex3f(0.0f, 1.0f, 0.0f);         // Top Of Triangle (Left)
		glColor3f(0.0f, 0.0f, 1.0f);             // Blue
		glVertex3f(-1.0f, -1.0f, -1.0f);         // Left Of Triangle (Left)
		glColor3f(0.0f, 1.0f, 0.0f);             // Green
		glVertex3f(-1.0f, -1.0f, 1.0f);         // Right Of Triangle (Left)
		glEnd();
	}
}
