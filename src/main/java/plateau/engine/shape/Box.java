package plateau.engine.shape;

import static org.lwjgl.opengl.GL11.*;

public class Box extends Shape {

    public Box(float sizeX, float sizeY, float sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }

    @Override
    public void render() {
        //Multi-colored side - FRONT
        glBegin(GL_POLYGON);

        glColor3d(1.0, 0.0, 0.0);
        glVertex3d(0.5, -0.5, -0.5);      // P1 is red
        glColor3d(0.0, 1.0, 0.0);
        glVertex3d(0.5, 0.5, -0.5);      // P2 is green
        glColor3d(0.0, 0.0, 1.0);
        glVertex3d(-0.5, 0.5, -0.5);      // P3 is blue
        glColor3d(1.0, 0.0, 1.0);
        glVertex3d(-0.5, -0.5, -0.5);      // P4 is purple

        glEnd();
        glBegin(GL_POLYGON);
        glColor3d(1.0, 1.0, 1.0);
        glVertex3d(0.5, -0.5, 0.5);
        glVertex3d(0.5, 0.5, 0.5);
        glVertex3d(-0.5, 0.5, 0.5);
        glVertex3d(-0.5, -0.5, 0.5);
        glEnd();

        // Purple side - RIGHT
        glBegin(GL_POLYGON);
        glColor3d(1.0, 0.0, 1.0);
        glVertex3d(0.5, -0.5, -0.5);
        glVertex3d(0.5, 0.5, -0.5);
        glVertex3d(0.5, 0.5, 0.5);
        glVertex3d(0.5, -0.5, 0.5);
        glEnd();

        // Green side - LEFT
        glBegin(GL_POLYGON);
        glColor3d(0.0, 1.0, 0.0);
        glVertex3d(-0.5, -0.5, 0.5);
        glVertex3d(-0.5, 0.5, 0.5);
        glVertex3d(-0.5, 0.5, -0.5);
        glVertex3d(-0.5, -0.5, -0.5);
        glEnd();

        // Blue side - TOP
        glBegin(GL_POLYGON);
        glColor3d(0.0, 0.0, 1.0);
        glVertex3d(0.5, 0.5, 0.5);
        glVertex3d(0.5, 0.5, -0.5);
        glVertex3d(-0.5, 0.5, -0.5);
        glVertex3d(-0.5, 0.5, 0.5);
        glEnd();

        // Red side - BOTTOM
        glBegin(GL_POLYGON);
        glColor3d(1.0, 0.0, 0.0);
        glVertex3d(0.5, -0.5, -0.5);
        glVertex3d(0.5, -0.5, 0.5);
        glVertex3d(-0.5, -0.5, 0.5);
        glVertex3d(-0.5, -0.5, -0.5);
        glEnd();
    }
}
