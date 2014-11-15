#include "InputHandler.h"

int old_x = 0;
int old_y = 0;
float speed = 1.5;


void InputHandler::mouseMove(GLFWwindow *window, double x, double y)
{
    float mouseDX = (x - old_x) * 0.16f;
    float mouseDY = (old_y - y) * 0.16f;


    old_x = x;
    old_y = y;
}

void InputHandler::keyPressed(GLFWwindow *window, int key, int scancode, int action, int mods)
{
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
        glfwSetWindowShouldClose(window, GL_TRUE);
}

void InputHandler::mousePressed(GLFWwindow *window, int button, int action, int mods)
{
}

