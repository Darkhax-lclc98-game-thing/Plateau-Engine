#include <iostream>
#include <vector>
#include "Opengl.h"

const int hWidth = 4096;
const int hHeight = 4096;

class World {
public:
    World();

    ~World();

    bool showTerrain;

//    std::vector buildingList;
//    std::vector heightmapList;

    float data[hWidth][hHeight];
    int intArray[hWidth][hHeight];
//    std::vector entityList;

    void init();

    void update();

    void render();
};
