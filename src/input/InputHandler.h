#ifndef INPUTHANDLER_H
#define INPUTHANDLER_H
#include "../renderer/RenderHandler.h"

extern EntityPlayer player;
class InputHandler {
public:

    static void mouseMove(GLFWwindow *window, double x, double y);

    static void keyPressed(GLFWwindow *window, int key, int scancode, int action, int mods);

    static void mousePressed(GLFWwindow *window, int button, int action, int mods);
};

#endif