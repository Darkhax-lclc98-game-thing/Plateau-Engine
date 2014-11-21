#include "World.h"

bool World::init()
{
    glGenBuffersARB(1, &vhVBOVertices);
    glBindBufferARB(GL_ARRAY_BUFFER_ARB, vhVBOVertices);
    glBufferDataARB(GL_ARRAY_BUFFER_ARB, vhVertexCount * 3 * sizeof(float), vhVertices, GL_STATIC_DRAW_ARB);

    delete[] vhVertices;
    vhVertices = NULL;

    return true;
}

bool World::create(char *hFileName, const int hWidth, const int hHeight)
{
    hmHeight = hHeight;
    hmWidth = hWidth;

    FILE *fp;

    fp = fopen(hFileName, "rb");

    fread(hHeightField, 1, hWidth * hHeight, fp);

    fclose(fp);

    vhVertexCount = (hHeight * hHeight * 6) / (hLOD * hLOD);
    vhVertices = new Vert[vhVertexCount];

    int nIndex = 0;
    float flX;
    float flZ;

    for (int hMapX = 0; hMapX < hmWidth; hMapX += hLOD) {
        for (int hMapZ = 0; hMapZ < hmHeight; hMapZ += hLOD) {
            for (int nTri = 0; nTri < 6; nTri++) {
                flX = (float) hMapX + ((nTri == 1 || nTri == 2 || nTri == 5) ? hLOD : 0);
                flZ = (float) hMapZ + ((nTri == 2 || nTri == 4 || nTri == 5) ? hLOD : 0);

                vhVertices[nIndex].x = flX;
                vhVertices[nIndex].y = hHeightField[(int) flX][(int) flZ];
                vhVertices[nIndex].z = flZ;

                nIndex++;
            }
        }
    }
    init();

    return true;
}

void World::render()
{
    glColor3f(0.5, .5, .5);

    glEnableClientState(GL_VERTEX_ARRAY);
    glBindBufferARB(GL_ARRAY_BUFFER_ARB, vhVBOVertices);
    glVertexPointer(3, GL_FLOAT, 0, (char *) NULL);

    glDrawArrays(GL_TRIANGLES, 0, vhVertexCount);

    glDisableClientState(GL_VERTEX_ARRAY);
}