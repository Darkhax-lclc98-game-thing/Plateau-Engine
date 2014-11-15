#include "Main.h"

void updateSomething()
{
    glfwPollEvents();
    rot = glfwGetTime() * 100.f;
}

void updateRender()
{
    float ratio;
    int width, height;
    glfwGetFramebufferSize(window, &width, &height);
    ratio = width / (float) height;
    glViewport(0, 0, width, height);
    glClear(GL_COLOR_BUFFER_BIT);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-ratio, ratio, -1.f, 1.f, 1.f, -1.f);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glRotatef(rot, 0.f, 0.f, 1.f);
    glBegin(GL_TRIANGLES);
    glColor3f(1.f, 0.f, 0.f);
    glVertex3f(-0.6f, -0.4f, 0.f);
    glColor3f(0.f, 1.f, 0.f);
    glVertex3f(0.6f, -0.4f, 0.f);
    glColor3f(0.f, 0.f, 1.f);
    glVertex3f(0.f, 0.6f, 0.f);
    glEnd();
    glfwSwapBuffers(window);
}


int fps()
{
    lastFpsTime += (timeDiff + sleepTime);
    fpsTime++;
    if (lastFpsTime >= 1000) {
        currentFps = fpsTime;
        lastFpsTime = 0;
        fpsTime = 0;
    }
    return currentFps;
}

void loop()
{
    while (!glfwWindowShouldClose(window)) {
        beginTime = time_ms();
        framesSkipped = 0;

        updateSomething();
        updateRender();

        timeDiff = time_ms() - beginTime;
        sleepTime = (FRAME_PERIOD - timeDiff);

        std::cout << fps() << std::endl;

        if (sleepTime > 0) {
            // sleep
            usleep(sleepTime);
        }
        while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
            // update stuff
            updateSomething();
            sleepTime += FRAME_PERIOD;
            framesSkipped++;
        }
    }
}

int main(void)
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

    loop();

    glfwDestroyWindow(window);
    glfwTerminate();
    exit(EXIT_SUCCESS);
}