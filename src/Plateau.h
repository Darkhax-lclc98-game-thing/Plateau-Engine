#pragma once

#include "renderer/RenderHandler.h"
#include "world/World.h"
#include "input/InputHandler.h"
#include "renderer/Display.h"

#include <math.h>

extern EntityPlayer *player;



class Plateau {
public:
    void initThread();
    void init(int argc, char **argv);
};

