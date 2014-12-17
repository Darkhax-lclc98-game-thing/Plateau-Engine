#include "InputHandler.h"

int old_x;
int old_y;
float speed = 0.45;

std::vector<int> keyList;

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
    std::cout << key << ":" << action << std::endl;
    if (key == config.KEY_PAUSE && action == GLFW_PRESS) {
        glfwSetWindowShouldClose(window, GL_TRUE);
    }

    if (action == GLFW_PRESS) {
        keyList.push_back(key);
    } else if (action == GLFW_RELEASE) {
        keyList.erase(std::remove(keyList.begin(), keyList.end(), key), keyList.end());
    }

}

void InputHandler::mousePressed(GLFWwindow *window, int button, int action, int mods)
{
}

void InputHandler::updateKeys(long delta)
{
    for (std::vector<int>::iterator it = keyList.begin(); it != keyList.end(); ++it) {
        if (*it == config.KEY_FORWARD) {
            move(0, -speed * delta);
        } else if (*it == config.KEY_BACKWARDS) {
            move(0, speed * delta);
        } else if (*it == config.KEY_LEFT) {
            move(-speed * delta, 0);
        } else if (*it == config.KEY_RIGHT) {
            move(speed * delta, 0);
        } else if (*it == GLFW_KEY_LEFT_SHIFT) {
            move(0, speed * delta, 0, true);
        } else if (*it == GLFW_KEY_SPACE) {
            move(0, -speed * delta, 0, true);
        }
    }

}
