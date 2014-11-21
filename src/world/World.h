#include <windows.h>
#include <stdio.h>
#include <GL/glew.h>
#include <ostream>

class Vert
{
public:
    float x;
    float y;
    float z;
};

class World
{
private:
    int hmHeight;
    int hmWidth;

    int vhVertexCount;
    Vert *vhVertices;

    unsigned int vhVBOVertices;

    bool init();

public:
    bool create(char *hFileName, const int hWidth, const int hHeight);

    void render();

    int hLOD;

    BYTE hHeightField[1024][1024];
};