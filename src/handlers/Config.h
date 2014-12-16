#ifndef CONFIG_H
#define CONFIG_H

#include <iostream>
#include <fstream>
#include <string>
#include <iostream>
#include <string>
#include "JsonBox.h"
#include <GLFW/glfw3.h>

class Config
{

public:
    int KEY_FORWARD;
    int KEY_BACKWARDS;
    int KEY_LEFT;
    int KEY_RIGHT;
    int KEY_PAUSE;

    int WINDOW_WIDTH;
    int WINDOW_HEIGHT;

    float MOUSE_ACCELERATION;

    bool VSYNC;

    int AUDIO_MASTER;
    int AUDIO_MUSIC;
    int AUDIO_AMBIENT;
    int AUDIO_ENVIRONMENT;
    int AUDIO_HOSTILE;
    int AUDIO_WEATHER;

    void readConfig(GLFWvidmode const *pConst);
};

#endif