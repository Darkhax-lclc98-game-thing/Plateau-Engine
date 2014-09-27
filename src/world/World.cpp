#include <stdio.h>
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
            //printf("%d, %d, %d\n", hMapX, hMapZ, data[hMapX][hMapZ]);
        }
    }
}