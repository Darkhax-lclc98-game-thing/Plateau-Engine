#ifndef MAIN_H
#define MAIN_H

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <stdlib.h>
#include <iostream>
#include <unistd.h>

#include "input/InputHandler.h"
#include "handlers/ErrorHandler.h"
#include "renderer/RenderHandler.h"
#include "handlers/Config.h"

#if defined(__WIN32__)

#include <windows.h>

long time_ms(void)
{
    return timeGetTime();
}

#else

#include <sys/time.h>

long time_ms(void)
{
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

#endif

int MAX_FPS = 60;
int MAX_FRAME_SKIPS = 5;
int FRAME_PERIOD = 1000 / MAX_FPS;

int sleepTime;
int framesSkipped;

int lastLoopTime = time_ms();
int fps;
int lastFpsTime;
int currentFps = 0;

GLFWwindow *window;

#endif