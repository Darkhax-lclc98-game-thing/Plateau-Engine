#include <GLFW/glfw3.h>

class InputHandler {
public:

    static void mouseMove(GLFWwindow *window, double x, double y);

    static void keyPressed(GLFWwindow *window, int key, int scancode, int action, int mods);

    static void mousePressed(GLFWwindow *window, int button, int action, int mods);
};
