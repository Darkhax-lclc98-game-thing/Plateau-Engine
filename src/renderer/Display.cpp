#include "Display.h"

void Display::set2D() {
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

void Display::set3D() {
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
    glDisable(GL_BLEND);
}

void Display::renderWorld() {
    fps++;

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    // checks timer to see if ticked
    // update physics

    // update heightmap and world render
    renderHandler->update();
    //world->update();

    glPushMatrix();
    glBegin(GL_POLYGON);

    glColor3f(1.0, 0.0, 0.0);
    glVertex3f(0.5, -0.5, -0.5);      // P1 is red
    glColor3f(0.0, 1.0, 0.0);
    glVertex3f(0.5, 0.5, -0.5);      // P2 is green
    glColor3f(0.0, 0.0, 1.0);
    glVertex3f(-0.5, 0.5, -0.5);      // P3 is blue
    glColor3f(1.0, 0.0, 1.0);
    glVertex3f(-0.5, -0.5, -0.5);      // P4 is purple

    glEnd();

    // White side - BACK
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


    //glFlush();

    //set2D();
    // render debug text
    //set3D();

    glutSwapBuffers();

}

void Display::resizeHandler(int w, int h) {
    // Prevent a divide by zero, when window is too short
    // (you cant make a window of zero width).
    if (h == 0)
        h = 1;
    renderHandler->initCamera(w, h);
}