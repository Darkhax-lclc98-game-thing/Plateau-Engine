#pragma once

#include "RenderHandler.h"
#include "World.h"
#include "InputHandler.h"
#include "Display.h"

#include <math.h>

extern EntityPlayer *player;



class Plateau {
public:
    void initThread();
    void init(int argc, char **argv);
};

