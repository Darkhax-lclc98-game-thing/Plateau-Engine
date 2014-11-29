#include "Main.h"

RenderHandler handler;
Config config;

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

//    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); //We don't want the old OpenGL
//    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); // We want OpenGL 3.3
//    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
//    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); // To make MacOS happy; should not be needed


    // load the config, if not their, make one
    const GLFWvidmode *mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    config.readConfig(mode);


    // creates gui
    window = glfwCreateWindow(mode->width, mode->height, "Simple example", glfwGetPrimaryMonitor(), NULL);

    if (!window) {
        glfwTerminate();
        exit(EXIT_FAILURE);
    }

    glfwMakeContextCurrent(window);

    glewExperimental = true; // Needed in core profile

    if (glewInit() != GLEW_OK) {
        fprintf(stderr, "Failed to initialize GLEW\n");
        exit(EXIT_FAILURE);
    }

    printf("OpenGL version supported by this platform (%s): \n", glGetString(GL_VERSION));
    // handle input
    glfwSetKeyCallback(window, InputHandler::keyPressed);
    glfwSetCursorPosCallback(window, InputHandler::mouseMove);
    glfwSetMouseButtonCallback(window, InputHandler::mousePressed);
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    handler.initCamera(mode->width, mode->height);

}

int main(void)
{

    init();
    loop();

    glfwDestroyWindow(window);
    glfwTerminate();
    exit(EXIT_SUCCESS);
}