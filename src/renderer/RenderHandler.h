#ifndef RENDERHANDLER_H
#define RENDERHANDLER_H

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <math.h>
#include <iostream>
#include "../Config.h"
#include "../world/World.h"
#include "../entity/player/EntityPlayer.h"

class RenderHandler
{
public:

    void initCamera(int width, int height);

    void update();
};

#endif
