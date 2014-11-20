#ifndef RENDERHANDLER_H
#define RENDERHANDLER_H

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include "../Config.h"
#include <math.h>
#include "../entity/player/EntityPlayer.h"


class RenderHandler
{
public:

    void initCamera();

    void update();
};

#endif
