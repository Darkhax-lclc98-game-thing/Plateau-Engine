#include "Plateau.h"

RenderHandler *renderHandler;
World *world;

void Plateau::initThread() {
    // handles the game registering information

    // handles core engine information
    renderHandler = new RenderHandler();
    renderHandler->update();

    world = new World();

}

void set2D() {
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0, CLIENT_WIDTH, 0, CLIENT_HEIGHT, -1, 1);
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();
    glEnable(GL_BLEND);
    glBlendFunc(GL_ONE, GL_ONE);
}

void set3D() {
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
    glDisable(GL_BLEND);
}

void renderWorld() {
    glClear(GL_COLOR_BUFFER_BIT || GL_DEPTH_BUFFER_BIT);
    // checks timer to see if ticked
    // update physics

    // update heightmap and world render
    renderHandler->update();
    world->update();

    glPushMatrix();
    glBegin(GL_POLYGON);
    glColor3f(1.0, 1.0, 1.0);
    glVertex3f(0.5, -0.5, 0.5);
    glVertex3f(0.5, 0.5, 0.5);
    glVertex3f(-0.5, 0.5, 0.5);
    glVertex3f(-0.5, -0.5, 0.5);
    glEnd();

// Purple side - RIGHT
    glBegin(GL_POLYGON);
    glColor3f(1.0, 0.0, 1.0);
    glVertex3f(0.5, -0.5, -0.5);
    glVertex3f(0.5, 0.5, -0.5);
    glVertex3f(0.5, 0.5, 0.5);
    glVertex3f(0.5, -0.5, 0.5);
    glEnd();

// Green side - LEFT
    glBegin(GL_POLYGON);
    glColor3f(0.0, 1.0, 0.0);
    glVertex3f(-0.5, -0.5, 0.5);
    glVertex3f(-0.5, 0.5, 0.5);
    glVertex3f(-0.5, 0.5, -0.5);
    glVertex3f(-0.5, -0.5, -0.5);
    glEnd();

// Blue side - TOP
    glBegin(GL_POLYGON);
    glColor3f(0.0, 0.0, 1.0);
    glVertex3f(0.5, 0.5, 0.5);
    glVertex3f(0.5, 0.5, -0.5);
    glVertex3f(-0.5, 0.5, -0.5);
    glVertex3f(-0.5, 0.5, 0.5);
    glEnd();

// Red side - BOTTOM
    glBegin(GL_POLYGON);
    glColor3f(1.0, 0.0, 0.0);
    glVertex3f(0.5, -0.5, -0.5);
    glVertex3f(0.5, -0.5, 0.5);
    glVertex3f(-0.5, -0.5, 0.5);
    glVertex3f(-0.5, -0.5, -0.5);
    glEnd();
    glPopMatrix();
    glFlush();

    //set2D();
    // render debug text
    //set3D();

    glutSwapBuffers();

}

void resizeHandler(int w, int h) {
// Prevent a divide by zero, when window is too short
    // (you cant make a window of zero width).
    if (h == 0)
        h = 1;
    renderHandler->initCamera(w, h);
}

void keyboard(unsigned char key, int x, int y) {
    switch (key) {
        default:
            printf("%d\n", key);
            break;
    }
    glutPostRedisplay();
}

int old_x=0;
int old_y=0;

void mouseMove(int x, int y) {
    float mouseDX = (old_x - x) * 0.16f;
    float mouseDY = (old_y - y) * 0.16f;

    if (player->getYaw() + mouseDX >= 360) {
        player->setYaw(player->getYaw() + mouseDX - 360);
    } else if (player->getYaw() + mouseDX < 0) {
        player->setYaw(360 - player->getYaw() + mouseDX);
    } else {
        player->setYaw(player->getYaw() + mouseDX);
    }

    int maxLookDown = -80;
    int maxLookUp = 80;

    if (player->getPitch() - mouseDY >= maxLookDown && player->getPitch() - mouseDY <= maxLookUp) {
        player->setPitch(player->getPitch() + -mouseDY);
    } else if (player->getPitch() - mouseDY < maxLookDown) {
        player->setPitch(maxLookDown);
    } else if (player->getPitch() - mouseDY > maxLookUp) {
        player->setPitch(maxLookUp);
    }

    old_x = x;
    old_y = y;

   // glutWarpPointer(CLIENT_WIDTH/2, CLIENT_HEIGHT/2);
    glutPostRedisplay();
    //printf(player->getYaw() + "\n");
}
void update(int value) {
    glutPostRedisplay();
    glutTimerFunc(25, update, 0);
}

void Plateau::init(int argc, char **argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(CLIENT_WIDTH, CLIENT_HEIGHT);
    glutInitWindowPosition(100, 100);
    glutCreateWindow("Game");
    initThread();

    glutSetCursor(GLUT_CURSOR_NONE);

    glutReshapeFunc(resizeHandler);
    glutDisplayFunc(renderWorld);
    glutIdleFunc(renderWorld);

    glutKeyboardFunc(keyboard);
    glutPassiveMotionFunc(mouseMove);

    glutTimerFunc(25, update, 0);

    glutMainLoop();
}
