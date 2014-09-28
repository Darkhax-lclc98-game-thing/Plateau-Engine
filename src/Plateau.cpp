#include <world/World.h>
#include "Plateau.h"

RenderHandler *renderHandler;

void Plateau::initThread() {
    // handles the game registering information


    // handles core engine information
    renderHandler = new RenderHandler();
    renderHandler->update();

    World* world = new World();
    world->update();
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

    set2D();
    // render debug text
    set3D();

    glutSwapBuffers();
}

void resizeHandler(int w, int h) {
// Prevent a divide by zero, when window is too short
    // (you cant make a window of zero width).
    if (h == 0)
        h = 1;
    renderHandler->initCamera(w, h);
}

void Plateau::init(int argc, char **argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowSize(CLIENT_WIDTH, CLIENT_HEIGHT);
    glutInitWindowPosition(100, 100);
    glutCreateWindow("Game");
    initThread();

    glutReshapeFunc(resizeHandler);
    glutDisplayFunc(renderWorld);
    glutIdleFunc(renderWorld);
    glutMainLoop();
}