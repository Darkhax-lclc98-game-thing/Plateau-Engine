#pragma once

#include "Plateau.h"

extern EntityPlayer *player;
extern int fps;
extern RenderHandler *renderHandler;

class Display {
public:

    void set2D();

    void set3D();

    static void renderWorld();

    static void resizeHandler(int w, int h);
};