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

    unsigned int tID[2];

    bool init(void);

public:
    bool create(char *hFileName, const int hWidth, const int hHeight);

    void render(void);

    int hLOD;

    BYTE hHeightField[1024][1024];
};