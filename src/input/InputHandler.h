#pragma once

#include "../Plateau.h"

extern EntityPlayer *player;

class InputHandler {
public:

    static void mouseMove(int x, int y);

    static void processNormalKeys(unsigned char key, int x, int y);

    static void move(int dx, int dz);

    static float toRadians(float degree);

    static void processSpecialKeys(int key, int x, int y);
};
