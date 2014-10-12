#include "InputHandler.h"

int old_x = 0;
int old_y = 0;
float speed = 1.5;

void InputHandler::processSpecialKeys(int key, int x, int y) {
    float mouseDX = 0;
    float mouseDY = 0;

    switch (key) {
        case GLUT_KEY_LEFT :
            mouseDX = 10 * 0.16;
            break;
        case GLUT_KEY_RIGHT         :
            mouseDX = -10 * 0.16;
            break;
        case GLUT_KEY_UP         :
            mouseDY = 10 * 0.16;
            break;
        case GLUT_KEY_DOWN         :
            mouseDY = -10 * 0.16;
            break;
    }

    if (player->getYaw() + mouseDX >= 360) {
        player->setYaw(player->getYaw() + mouseDX - 360);
    } else if (player->getYaw() + mouseDX < 0) {
        player->setYaw(360 - player->getYaw() + mouseDX);
    } else {
        player->setYaw(player->getYaw() + mouseDX);
    }

    int maxLookDown = -80;
    int maxLookUp = 80;

    if (player->getPitch() - mouseDY >= maxLookDown && player->getPitch() - mouseDY <= maxLookUp) {
        player->setPitch(player->getPitch() + -mouseDY);
    } else if (player->getPitch() - mouseDY < maxLookDown) {
        player->setPitch(maxLookDown);
    } else if (player->getPitch() - mouseDY > maxLookUp) {
        player->setPitch(maxLookUp);
    }
}

float InputHandler::toRadians(float degree) {
    return degree * (M_PI / 180);
}

void InputHandler::move(int dx, int dz) {
    float x = (float) (player->getMotionX() - dx * (float) sin(toRadians(player->getYaw() - 90)) + dz * sin(toRadians(-player->getYaw())));
    float z = (float) (player->getMotionZ() + dx * (float) cos(toRadians(player->getYaw() - 90)) + dz * cos(toRadians(-player->getYaw())));

    player->setMotionX(x);
    player->setMotionZ(z);
}

void InputHandler::processNormalKeys(unsigned char key, int x, int y) {
    switch (key) {
        case 'w':
            move(0, -speed);
            break;
        case 's':
            move(0, speed);
            break;
        case 'a':
            move(-speed, 0);
            break;
        case 'd':
            move(speed, 0);
            break;
    }
    //printf("%d\n", key);

    // glutPostRedisplay();
}

void InputHandler::mouseMove(int x, int y) {
    float mouseDX = (x - old_x) * 0.16f;
    float mouseDY = (old_y - y) * 0.16f;

    if (player->getYaw() + mouseDX >= 360) {
        player->setYaw(player->getYaw() + mouseDX - 360);
    } else if (player->getYaw() + mouseDX < 0) {
        player->setYaw(360 - player->getYaw() + mouseDX);
    } else {
        player->setYaw(player->getYaw() + mouseDX);
    }

    int maxLookDown = -80;
    int maxLookUp = 80;

    if (player->getPitch() - mouseDY >= maxLookDown && player->getPitch() - mouseDY <= maxLookUp) {
        player->setPitch(player->getPitch() + -mouseDY);
    } else if (player->getPitch() - mouseDY < maxLookDown) {
        player->setPitch(maxLookDown);
    } else if (player->getPitch() - mouseDY > maxLookUp) {
        player->setPitch(maxLookUp);
    }

    old_x = x;
    old_y = y;
}