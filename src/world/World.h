#include <iostream>
#include <vector>
#include <stdio.h>
#include "../Opengl.h"

const int hWidth = 4096;
const int hHeight = 4096;
const int chunkSize = 32;

class World {
public:
    World();

    ~World();

    bool showTerrain;

//    std::vector buildingList;
    std::vector<int> heightmapList;

    float data[hWidth][hHeight];
    int intArray[hWidth][hHeight];
//    std::vector entityList;

    void init();

    void update();

    void render();
};
