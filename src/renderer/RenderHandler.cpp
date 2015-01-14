#include "RenderHandler.h"

EntityPlayer player;
World world;
extern Config config;

Mesh *mesh;
GLuint testWorld;
ImageLoader loader;

void perspectiveGL(GLdouble fovY, GLdouble aspect, GLdouble zNear, GLdouble zFar)
{
    GLdouble fW, fH;
    fH = tan(fovY / 360 * 3.1415926535897932384626433832795) * zNear;
    fW = fH * aspect;
    glFrustum(-fW, fW, -fH, fH, zNear, zFar);
}

std::string NumberToString(float num)
{
    std::stringstream ss;
    ss << num;
    return ss.str();
}

void RenderHandler::loadModel()
{
    mesh = new Mesh("Grass_01.obj");
}


void RenderHandler::initCamera(int width, int height)
{
    glViewport(0, 0, width, height);

    glClearColor(0, 0.75f, 1, 1);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    perspectiveGL(config.FOV, (float) config.WINDOW_WIDTH / config.WINDOW_HEIGHT, 0.1f, 5000);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glEnable(GL_DEPTH_TEST); // Depth Testing
    glDepthFunc(GL_LEQUAL);
    //glDisable(GL_CULL_FACE);
    //glCullFace(GL_BACK);

    player.setY(25);

    world.create("height.bmp", 4096, 2048, 8);
    testWorld = loader.loadTGA("color.tga");

    loadModel();
}

void RenderHandler::handlePlayer()
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
    renderText("X: " + NumberToString(player.getX()) + " Y: " + NumberToString(player.getY()) + " Z: " + NumberToString(player.getZ()), GLUT_BITMAP_9_BY_15, 10, 10);
    glPopAttrib();
}

void RenderHandler::handleRender(int fps)
{
    renderText("FPS: " + NumberToString(fps), GLUT_BITMAP_9_BY_15, 10, 25);

    glPushMatrix();
    mesh->draw();
    glPopMatrix();

    glPushMatrix();
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, testWorld);
    world.render();
    glBindTexture(GL_TEXTURE_2D, 0);
    glDisable(GL_TEXTURE_2D);
    glPopMatrix();
}

void RenderHandler::update(int fps)
{
    handlePlayer();
    handleRender(fps);
}

void RenderHandler::renderText(std::string text, void *font, int x, int y)
{
    glPushMatrix();
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    gluOrtho2D(0, config.WINDOW_WIDTH, 0, config.WINDOW_HEIGHT);
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();

    glRasterPos2i(x, y);
    for (std::string::iterator i = text.begin(); i != text.end(); ++i) {
        char c = *i;
        glColor4d(1.0f, 1.0f, 1.0f, 1.0f);
        glutBitmapCharacter(font, c);
    }

    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
    glPopMatrix();
}