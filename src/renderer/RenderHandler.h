#ifndef RENDERHANDLER_H
#define RENDERHANDLER_H

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <gl/glut.h>

#include <math.h>
#include <iostream>
#include <sstream>

#include "ImageLoader.h"
#include "../handlers/Config.h"
#include "../world/World.h"
#include "../entity/player/EntityPlayer.h"
#include "../obj/tiny_obj_loader.h"

class RenderHandler
{
public:

    void initCamera(int width, int height);

    void update(int fps);

    void renderText(std::string text, void *font, int x, int y);

private:
    void handlePlayer();

    void handleRender(int fps);

    void loadModel();
};

#endif