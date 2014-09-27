#include <iostream>
#include <vector>
#include "Opengl.h"

const int hWidth = 1000;
const int hHeight = 1000;

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
