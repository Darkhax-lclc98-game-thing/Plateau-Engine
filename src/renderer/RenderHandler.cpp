#include "RenderHandler.h"

EntityPlayer *player;

RenderHandler::RenderHandler() {
    player = new EntityPlayer;
    initCamera(CLIENT_WIDTH, CLIENT_HEIGHT);
}

void RenderHandler::initCamera(int width, int height) {
    glViewport(0, 0, width, height);

    glClearColor(0, 0.75f, 1, 1);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(60, (float) width / height, 0.1f, 1000);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
   // glDepthFunc(GL_LEQUAL);
   // glEnable(GL_DEPTH_TEST);

   // glEnable(GL_CULL_FACE);
   // glCullFace(GL_BACK);
}

void RenderHandler::update() {
    glLoadIdentity();
    glPushAttrib(GL_TRANSFORM_BIT);
    glMatrixMode(GL_MODELVIEW);

    glRotatef(player->getRoll(), 0, 0, 1);

    //if (player.is3rdPerson) {
    //    glTranslatef(0.0F, 0.0F, -player.thirdPersonDistance);
    //}

    glRotatef(player->getPitch(), 1, 0, 0);
    glRotatef(player->getYaw(), 0, 1, 0);

    glTranslated(-player->getX(), -player->getY(), -player->getZ());
    glPopAttrib();

    // check if player is null
    // update player
    // update players world
    // update players render
}