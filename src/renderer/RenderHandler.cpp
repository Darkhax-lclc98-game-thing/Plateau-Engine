#include <iostream>
#include "RenderHandler.h"
#include "../world/World.h"

EntityPlayer player;
World world;

void perspectiveGL(GLdouble fovY, GLdouble aspect, GLdouble zNear, GLdouble zFar)
{
    const GLdouble pi = 3.1415926535897932384626433832795;

    GLdouble fW, fH;
    fH = tan((fovY / 2) / 180 * pi) * zNear;
    fH = tan(fovY / 360 * pi) * zNear;
    fW = fH * aspect;
    glFrustum(-fW, fW, -fH, fH, zNear, zFar);
}

void RenderHandler::initCamera()
{
    glViewport(0, 0, CLIENT_WIDTH, CLIENT_HEIGHT);

    glClearColor(0, 0.75f, 1, 1);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    perspectiveGL(60, (float) CLIENT_WIDTH / CLIENT_HEIGHT, 0.1f, 1000);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glEnable(GL_DEPTH_TEST); // Depth Testing
    glDepthFunc(GL_LEQUAL);


    world.hLOD = 8;
    world.create("surface.bmp", 1024, 1024);

    player.setX(0);
    player.setY(50);
    player.setZ(0);

}

void RenderHandler::update()
{
    player.update();
    glPushMatrix();

    world.render();
    glPopMatrix();

    glLoadIdentity();
    glPushAttrib(GL_TRANSFORM_BIT);
    glMatrixMode(GL_MODELVIEW);

    glRotatef(-player.getRoll(), 0, 0, 1);

    //if (player.is3rdPerson) {
    //    glTranslatef(0.0F, 0.0F, -player.thirdPersonDistance);
    //}

    glRotatef(player.getPitch(), 1, 0, 0);
    glRotatef(player.getYaw(), 0, 1, 0);

    glTranslated(-player.getX(), -player.getY(), -player.getZ());
    std::cout << player.getX() << " " << player.getY() << " " << player.getZ() << std::endl;
    glPopAttrib();


}