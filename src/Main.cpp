#include "Main.h"

RenderHandler handler;

void update()
{
    glfwPollEvents();
}

//int fps()
//{
//    lastFpsTime += (timeDiff + sleepTime);
//    fpsTime++;
//    if (lastFpsTime >= 1000) {
//        currentFps = fpsTime;
//        lastFpsTime = 0;
//        fpsTime = 0;
//    }
//    return currentFps;
//}



void loop()
{
    while (!glfwWindowShouldClose(window)) {


        int startTime = time_ms();
        long delta = startTime - lastLoopTime;
        lastLoopTime = startTime;
        lastFpsTime += delta;
        framesSkipped = 0;
        fps++;

        if (lastFpsTime >= 1000) {
            std::cout << fps << std::endl;
            lastFpsTime = 0;
            fps = 0;
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        update();
        handler.update();
        glfwSwapBuffers(window);

        sleepTime = (FRAME_PERIOD - delta);

        if (sleepTime > 0) {
            // sleep
            usleep(sleepTime * 2000);
        }
        while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
            // update stuff
            update();
            sleepTime += FRAME_PERIOD;
            framesSkipped++;
        }
    }
}

void init()
{
    glfwSetErrorCallback(ErrorHandler::error_callback);
    if (!glfwInit())
        exit(EXIT_FAILURE);

    // creates gui
    window = glfwCreateWindow(640, 480, "Simple example", NULL, NULL);
    if (!window) {
        glfwTerminate();
        exit(EXIT_FAILURE);
    }

    glfwMakeContextCurrent(window);

    // handle input
    glfwSetKeyCallback(window, InputHandler::keyPressed);
    glfwSetCursorPosCallback(window, InputHandler::mouseMove);
    glfwSetMouseButtonCallback(window, InputHandler::mousePressed);
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    handler.initCamera();
}

int main(void)
{

    init();
    loop();

    glfwDestroyWindow(window);
    glfwTerminate();
    exit(EXIT_SUCCESS);
}