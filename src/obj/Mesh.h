#ifndef MESH_H
#define MESH_H

#include <GL/glew.h>
#include <vector>
#include <iostream>

#include "tiny_obj_loader.h"
#include "png/lodepng.h"
#include "renderer/ImageLoader.h"

class Mesh
{
public:
    Mesh(const char *filename);

    void draw();

private:

    unsigned int totalShapes;
    std::vector<unsigned int> verticesHandles;
    std::vector<unsigned int> uvHandles;
    std::vector<unsigned int> normalsHandles;
    std::vector<unsigned int> indicesHandles;
    std::vector<unsigned int> meshSizes;
    std::vector<unsigned int> textures;

    void loadTexture(tinyobj::material_t *material, int spot);
};

#endif