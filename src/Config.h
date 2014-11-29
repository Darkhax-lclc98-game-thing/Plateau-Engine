#ifndef CONFIG_H
#define CONFIG_H

#include <iostream>
#include <fstream>
#include <string>
#include <iostream>
#include <string>
#include "json2/JsonBox/Value.h"
#include "json2/JsonBox/Object.h"
#include <GLFW/glfw3.h>

class Config
{

public:
    int KEY_FORWARD;
    int KEY_BACKWARDS;
    int KEY_LEFT;
    int KEY_RIGHT;
    int WINDOW_WIDTH;
    int WINDOW_HEIGHT;

    float MOUSE_ACCELERATION;

    void readConfig();
};

#endif