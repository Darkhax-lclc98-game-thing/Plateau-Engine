#ifndef WORLD_H
#define WORLD_H

#include <windows.h>
#include <stdio.h>
#include <GL/glew.h>
#include <ostream>
#include <iostream>

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
    int hmHeight;
    int hmWidth;

    int vhVertexCount;
    Vert *vhVertices;
    TexCoord *vhTexCoords;

    unsigned int vhVBOVertices;
    unsigned int vhVBOTexCoords;

    bool init(void);

public:
    bool create(char const *hFileName, const int hWidth, const int hHeight);

    void render(void);

    int hLOD;

    BYTE hHeightField[4096][2048];
};

#endif