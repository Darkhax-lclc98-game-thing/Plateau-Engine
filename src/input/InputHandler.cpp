#include <ostream>
#include <iostream>
#include "InputHandler.h"

#define M_PI        3.14159265358979323846

int old_x = 0;
int old_y = 0;
float speed = 0.75;

void InputHandler::mouseMove(GLFWwindow *window, double x, double y)
{
    float mouseDX = (x - old_x) * 0.16f;
    float mouseDY = (old_y - y) * 0.16f;

    if (player.getYaw() + mouseDX >= 360) {
        player.setYaw(player.getYaw() + mouseDX - 360);
    } else if (player.getYaw() + mouseDX < 0) {
        player.setYaw(360 - player.getYaw() + mouseDX);
    } else {
        player.setYaw(player.getYaw() + mouseDX);
    }

    int maxLookDown = -80;
    int maxLookUp = 80;

    if (player.getPitch() - mouseDY >= maxLookDown && player.getPitch() - mouseDY <= maxLookUp) {
        player.setPitch(player.getPitch() + -mouseDY);
    } else if (player.getPitch() - mouseDY < maxLookDown) {
        player.setPitch(maxLookDown);
    } else if (player.getPitch() - mouseDY > maxLookUp) {
        player.setPitch(maxLookUp);
    }

    old_x = x;
    old_y = y;
}

float toRadians(float degree)
{
    return degree * (M_PI / 180);
}

void move(float dx, float dy, float dz, bool yMovement)
{
    float x = (float) (player.getMotionX() - dx * (float) sin(toRadians(player.getYaw() - 90)) + dz * sin(toRadians(-player.getYaw())));
    float y = player.getMotionY();
    if (yMovement)
        y = (float) (player.getMotionY() + dy * (float) sin(toRadians(player.getPitch() - 90)) + dz * sin(toRadians(player.getPitch())));
    float z = (float) (player.getMotionZ() + dx * (float) cos(toRadians(player.getYaw() - 90)) + dz * cos(toRadians(-player.getYaw())));

    player.setMotionX(x);
    player.setMotionY(y);
    player.setMotionZ(z);
}

void move(float dx, float dz)
{
    move(dx, 0, dz, false);
}


void InputHandler::keyPressed(GLFWwindow *window, int key, int scancode, int action, int mods)
{
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
        glfwSetWindowShouldClose(window, GL_TRUE);
    if (key == GLFW_KEY_W) {
        move(0, -speed);
    }
    if (key == GLFW_KEY_S) {
        move(0, speed);
    }
    if (key == GLFW_KEY_A) {
        move(-speed, 0);
    }
    if (key == GLFW_KEY_D) {
        move(speed, 0);
    }

}

void InputHandler::mousePressed(GLFWwindow *window, int button, int action, int mods)
{
}

