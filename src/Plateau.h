#include "Opengl.h"

const static int width = 800;
const static int height = 450;

class Plateau {
public:
    void initThread();

    static int getWidth() {
        return width;
    }

    static int getHeight() {
        return height;
    }

    void init(int argc, char **argv);
};

