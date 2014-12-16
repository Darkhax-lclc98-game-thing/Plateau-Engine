#ifndef MESH_H
#define MESH_H


#include <GL/glew.h>
#include <vector>

#include "tiny_obj_loader.h"
#include "png/lodepng.h"

class Mesh
{
public:
    Mesh(const char *filename);

    void draw();

private:
    unsigned int totalShapes;
    std::vector<unsigned char> verticesHandles;
    std::vector<unsigned char> uvHandles;
    std::vector<unsigned char> normalsHandles;
    std::vector<unsigned char> indicesHandles;
    std::vector<unsigned char> meshSizes;
    std::vector<GLuint> textures;

    void loadTexture(tinyobj::material_t *material, int spot);
};

#endif