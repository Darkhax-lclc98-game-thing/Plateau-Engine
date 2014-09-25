#include "Plateau.h"

void Plateau::initThread() {
    // handles the game registering information


    // handles core engine information
   // RenderHandler* renderHandler = new RenderHandler();
}

void display() {
    glClear(GL_COLOR_BUFFER_BIT || GL_DEPTH_BUFFER_BIT);

    // checks timer to see if ticked
    // update physics

    // update heightmap and world render

    // set 2d
    // render information
    // set 3d
    glutSwapBuffers();
}

void Plateau::init(int argc, char **argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(800, 450);
    glutInitWindowPosition(100, 100);
    glutCreateWindow("Game");
    initThread();
    glutDisplayFunc(display);
    glutMainLoop();
}