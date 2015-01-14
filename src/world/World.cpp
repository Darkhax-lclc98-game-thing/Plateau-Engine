#include "World.h"

bool World::init(void)
{
    glGenBuffers(1, &vhVBOVertices);
    glBindBuffer(GL_ARRAY_BUFFER, vhVBOVertices);
    glBufferData(GL_ARRAY_BUFFER, vhVertexCount * 2 * sizeof(float), vhVertices, GL_STATIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, 0);

    glGenBuffers(1, &vhVBOTexCoords);
    glBindBuffer(GL_ARRAY_BUFFER, vhVBOTexCoords);
    glBufferData(GL_ARRAY_BUFFER, vhVertexCount * 2 * sizeof(float), vhTexCoords, GL_STATIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, 0);

    delete[] vhVertices;
    vhVertices = NULL;

    delete[] vhTexCoords;
    vhTexCoords = NULL;
    return true;
}

bool World::create(const char *hFileName, const int hWidth, const int hHeight, int hLOD)
{
    FILE *fp = fopen(hFileName, "rb");


    fread(hHeightField, 1, hWidth * hHeight, fp);

    fclose(fp);

    vhVertexCount = (hWidth * hHeight * 6) / (hLOD * hLOD);
    vhVertices = new Vert[vhVertexCount];
    vhTexCoords = new TexCoord[vhVertexCount];

    int nIndex = 0;
    float flX;
    float flZ;

    for (int hMapX = 0; hMapX < hWidth; hMapX += hLOD) {
        for (int hMapZ = 0; hMapZ < hHeight; hMapZ += hLOD) {
            for (int nTri = 0; nTri < 6; nTri++) {
                flX = (float) hMapX + ((nTri == 1 || nTri == 2 || nTri == 5) ? hLOD : 0);
                flZ = (float) hMapZ + ((nTri == 2 || nTri == 4 || nTri == 5) ? hLOD : 0);
                vhVertices[nIndex].x = flX;
                vhVertices[nIndex].y = hHeightField[(int) flX][(int) flZ];
                vhVertices[nIndex].z = flZ;

                vhTexCoords[nIndex].u = flX / hWidth;
                vhTexCoords[nIndex].v = flZ / hHeight;
                nIndex++;
            }
        }
    }
    init();

    return true;
}

void World::render(void)
{
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_TEXTURE_COORD_ARRAY);

    glBindBuffer(GL_ARRAY_BUFFER, vhVBOVertices);
    glVertexPointer(3, GL_FLOAT, 0, 0);

    glBindBuffer(GL_ARRAY_BUFFER, vhVBOTexCoords);
    glTexCoordPointer(2, GL_FLOAT, 0, 0);

    glDrawArrays(GL_TRIANGLES, 0, vhVertexCount);

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindTexture(GL_TEXTURE_2D, 0);

    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);
}