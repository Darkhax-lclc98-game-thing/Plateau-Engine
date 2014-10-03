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
            // printf("%d, %d, %d\n", hMapX, hMapZ, data[hMapX][hMapZ]);
        }
    }

    // smooth terrain

    // setup render
    int xTemp = 0;
    int zTemp = 0;
    int displayList = -1;

    for (int i = 0; i < hWidth / chunkSize * hHeight / chunkSize; i++) {
        displayList = glGenLists(1);
        displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);

        if (zTemp + chunkSize > hHeight - 1) {
            zTemp = 0;
            xTemp += chunkSize;
        }

        if (xTemp > hWidth - chunkSize - 1) {
            glEndList();
            break;
        }

        for (int z = zTemp; z < zTemp + chunkSize; z++) {
            glBegin(GL_QUAD_STRIP);
            for (int x = xTemp; x < xTemp + chunkSize + 1; x++) {
                glTexCoord2f((float) x / hWidth, (float) z / hHeight);
                glVertex3f(x, data[x][z], z);
                glVertex3f(x, data[x][z + 1], z + 1);
                intArray[x][z] = displayList;
            }
            glEnd();
        }

        if (zTemp % chunkSize == 0) {
            zTemp += chunkSize;
        }

        glEndList();

    }
}

void World::update() {
    glPushMatrix();
    render();
    glPopMatrix();
}

void World::render() {
    //EntityPlayer player;
    // = Plateau.getPlayer();
    glEnable(GL_TEXTURE_2D);

    // ResourceLoader.bindTextures(texture());

    if (true) {//showTerrain) {
        for (int x = 0; x < hWidth / chunkSize; x++) {
            for (int z = 0; z < hHeight / chunkSize; z++) {
                glCallList(intArray[x * chunkSize][z * chunkSize]);
            }
        }
    } else {
        /*
        for (int z = -5; z < 5; z++) {

            for (int x = -5; x < 5; x++) {
                int tempX = chunkSize * x;
                int tempZ = chunkSize * z;
                if (player.getX() + tempX > 0 && player.getZ() + tempZ > 0 && player.getX() + tempX < hWidth && player.getZ() + tempZ < hHeight) {
                    //heightmapList.insert(1, intArray[(int) (tempX + player.getX())][(int) (tempZ + player.getZ())]);
                }
            }
        }
        for (int chunk : heightmapList) {
            glCallList(chunk);
        }
        heightmapList.clear();
        */
    }

    glDisable(GL_TEXTURE_2D);
}
