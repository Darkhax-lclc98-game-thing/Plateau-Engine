#pragma once

#include "Opengl.h"
#include "Config.h"
#include "EntityPlayer.h"


class RenderHandler {
public:
    RenderHandler();

    ~RenderHandler();

    void update();

    void initCamera(int width, int height);
};
