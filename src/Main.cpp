#include "Main.h"

RenderHandler renderHandler;
Config config;

void loop()
{
    while (!glfwWindowShouldClose(window)) {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        int startTime = time_ms();
        long delta = startTime - lastLoopTime;
        lastLoopTime = startTime;
        lastFpsTime += delta;
        framesSkipped = 0;
        fps++;

        if (lastFpsTime >= 1000) {
            currentFps = fps;
            lastFpsTime = 0;
            fps = 0;

        }
        InputHandler::updateKeys(delta);

        renderHandler.update(currentFps);
        glfwSwapBuffers(window);
        glfwPollEvents();
        if (config.VSYNC) {
            sleepTime = (FRAME_PERIOD - delta);

            if (sleepTime > 0) {
                // sleep
                usleep(sleepTime * 2000);
            }
            while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                // update stuff
                sleepTime += FRAME_PERIOD;
                framesSkipped++;
            }
        }
    }
}

void init()
{

    glfwSetErrorCallback(ErrorHandler::error_callback);

    if (!glfwInit())
        exit(EXIT_FAILURE);

    // load the config, if not their, make one
    config.readConfig();


    // creates gui
    window = glfwCreateWindow(config.WINDOW_WIDTH, config.WINDOW_HEIGHT, "Simple example", NULL /*glfwGetPrimaryMonitor()*/, NULL);

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

    renderHandler.initCamera(config.WINDOW_WIDTH, config.WINDOW_HEIGHT);

}

int main(void)
{
    init();
    loop();

    glfwDestroyWindow(window);
    glfwTerminate();
    exit(EXIT_SUCCESS);
}