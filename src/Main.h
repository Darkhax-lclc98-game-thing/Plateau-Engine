#include <GLFW/glfw3.h>
#include <stdlib.h>
#include "ErrorHandler.h"
#include <iostream>
#include "input/InputHandler.h"
#include <unistd.h>

// Used to measure intervals and absolute times
typedef int64_t msec_t;

// Get current time in milliseconds from the Epoch (Unix)
// or the time the system started (Windows).
msec_t time_ms(void);

#if defined(__WIN32__)

#include <windows.h>

msec_t time_ms(void)
{
    return timeGetTime();
}

#else

#include <sys/time.h>

msec_t time_ms(void)
{
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return (msec_t)tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

#endif

int MAX_FPS = 60;
int MAX_FRAME_SKIPS = 5;
int FRAME_PERIOD = 1000 / MAX_FPS;

long beginTime;
long timeDiff;
int sleepTime;
int framesSkipped;

int lastFpsTime = 0;
int fpsTime = 0;
int currentFps = 0;

float rot = 0;
GLFWwindow *window;