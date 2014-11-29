#include "RenderHandler.h"

EntityPlayer player;
World world;
extern Config config;

void perspectiveGL(GLdouble fovY, GLdouble aspect, GLdouble zNear, GLdouble zFar)
{
    GLdouble fW, fH;
    fH = tan(fovY / 360 * 3.1415926535897932384626433832795) * zNear;
    fW = fH * aspect;
    glFrustum(-fW, fW, -fH, fH, zNear, zFar);
}

void RenderHandler::initCamera(int width, int height)
{
    glViewport(0, 0, width, height);

    glClearColor(0, 0.75f, 1, 1);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    perspectiveGL(60, (float) config.WINDOW_WIDTH / config.WINDOW_HEIGHT, 0.1f, 1000);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glEnable(GL_DEPTH_TEST); // Depth Testing
    glDepthFunc(GL_LEQUAL);
    glDisable(GL_CULL_FACE);
    glCullFace(GL_BACK);

    player.setY(25);
    world.hLOD = 8;
    world.create("heightField.raw", 1024, 1024);
}

void RenderHandler::update()
{
    player.update();

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

    glPushMatrix();
    GLfloat vertices[] =
            {
                    -1, -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1,
                    1, -1, -1, 1, -1, 1, 1, 1, 1, 1, 1, -1,
                    -1, -1, -1, -1, -1, 1, 1, -1, 1, 1, -1, -1,
                    -1, 1, -1, -1, 1, 1, 1, 1, 1, 1, 1, -1,
                    -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1, -1,
                    -1, -1, 1, -1, 1, 1, 1, 1, 1, 1, -1, 1
            };

    GLfloat colors[] =
            {
                    0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0,
                    1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0,
                    0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0,
                    0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0,
                    0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0,
                    0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1
            };

    /* We have a color array and a vertex array */
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_COLOR_ARRAY);
    glVertexPointer(3, GL_FLOAT, 0, vertices);
    glColorPointer(3, GL_FLOAT, 0, colors);

    /* Send data : 24 vertices */
    glDrawArrays(GL_QUADS, 0, 24);

    /* Cleanup states */
    glDisableClientState(GL_COLOR_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);
    glPopMatrix();

    glPushMatrix();
    world.render();
    glPopMatrix();
}