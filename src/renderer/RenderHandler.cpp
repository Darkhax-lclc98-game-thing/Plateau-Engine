#include "RenderHandler.h"

RenderHandler::RenderHandler() {
//    initCamera(Plateau::getWidth(), 0);
}

void RenderHandler::initCamera(int width, int height) {
    glViewport(0, 0, width, height);

    glClearColor(0, 0.75f, 1, 1);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(60, (float) width / height, 0.1f, 1000);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glDepthFunc(GL_LEQUAL);
    glEnable(GL_DEPTH_TEST);

    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
}