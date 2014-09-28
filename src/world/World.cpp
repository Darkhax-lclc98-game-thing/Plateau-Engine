#include "World.h"

World::World() {
    init();
}

void World::init() {
    FILE *fp;

    fp = fopen("surface.bmp", "rb");

    fread(data, 1, hWidth * hHeight, fp);

    fclose(fp);

    for (int hMapX = 0; hMapX < hWidth; hMapX++) {
        for (int hMapZ = 0; hMapZ < hHeight; hMapZ++) {
            printf("%d, %d, %d\n", hMapX, hMapZ, data[hMapX][hMapZ]);
        }
    }

    // smooth terrain

    // setup render
    int xTemp = 0;
    int zTemp = 0;
    int displayList = -1;

    for (int i = 0; i < hWidth / chunkSize * hHeight / chunkSize; i++) {
        displayList = glGenList(1);
        glNewList(displayList, GL_COMPILE);
        
    }
}

void World::update() {

}

void World::render() {

}