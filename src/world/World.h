#ifndef WORLD_H
#define WORLD_H

#include <stdio.h>
#include <GL/glew.h>
#include <ostream>
#include <iostream>
#include <vector>
#include <stdlib.h>
typedef unsigned char BYTE;

class Vert
{
public:
    float x;
    float y;
    float z;
};

class TexCoord
{
public:
    float u;
    float v;
};

class World
{
private:

    int vhVertexCount;
    Vert *vhVertices;
    TexCoord *vhTexCoords;

    unsigned int vhVBOVertices;
    unsigned int vhVBOTexCoords;

    bool init(void);

public:
    bool create(const char *hFileName, int hWidth, int hHeight, int hLOD);

    void render(void);

    BYTE hHeightField[4096][2048];
};

#endif