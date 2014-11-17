#ifndef RENDERHANDLER_H
#define RENDERHANDLER_H

#include "../Config.h"
#include <GL/gl.h>
#include <math.h>
#include "../entity/player/EntityPlayer.h"

class RenderHandler
{
public:

    void initCamera();

    void update();
};

#endif
