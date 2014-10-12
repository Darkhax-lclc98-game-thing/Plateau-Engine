#include "Plateau.h"

int fps = 0;

RenderHandler *renderHandler;
World *world;

void Plateau::initThread() {
    // handles the game registering information
    // handles core engine information

    renderHandler = new RenderHandler();
    world = new World();
}

void fpsCounter(int value) {
    std::cout << "FPS: " << fps << std::endl;
    fps = 0;

    //glutPostRedisplay();
    glutTimerFunc(1000, fpsCounter, 0);
}

void Plateau::init(int argc, char **argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(CLIENT_WIDTH, CLIENT_HEIGHT);
    glutInitWindowPosition(100, 100);
    glutCreateWindow("Game");
    initThread();

    glutSetCursor(GLUT_CURSOR_NONE);
    glutReshapeFunc(Display::resizeHandler);
    glutDisplayFunc(Display::renderWorld);
    glutIdleFunc(Display::renderWorld);

    glutKeyboardFunc(InputHandler::processNormalKeys);
    glutSpecialFunc(InputHandler::processSpecialKeys);
    glutPassiveMotionFunc(InputHandler::mouseMove);

    glutTimerFunc(1000, fpsCounter, 0);

    glutMainLoop();
}

