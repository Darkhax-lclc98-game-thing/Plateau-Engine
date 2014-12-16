#include <iostream>
#include "Mesh.h"
#include "renderer/ImageLoader.h"

Mesh::Mesh(const char *filename)
{
    std::vector<tinyobj::shape_t> shapes;
    std::vector<tinyobj::material_t> materials;

    tinyobj::LoadObj(shapes, materials, filename);

    for (int i = 0; i < shapes.size(); i += 1) {
        tinyobj::mesh_t *mesh = &shapes[i].mesh;

        glEnableVertexAttribArray(0);
        GLuint verticesHandle = 0;
        glGenBuffers(1, &verticesHandle);
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandle);
        glBufferData(GL_ARRAY_BUFFER, mesh->positions.size() * sizeof(float), &mesh->positions[0], GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
        verticesHandles.push_back(verticesHandle);

        if (mesh->normals.size() > 0) {
            GLuint normalsHandle = 0;
            glEnableVertexAttribArray(1);
            glGenBuffers(1, &normalsHandle);
            glBindBuffer(GL_ARRAY_BUFFER, normalsHandle);
            glBufferData(GL_ARRAY_BUFFER, mesh->normals.size() * sizeof(float), &mesh->normals[0], GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, 0);
            normalsHandles.push_back(normalsHandle);
        }
        else {
            normalsHandles.push_back(0);
        }

        if (mesh->texcoords.size() > 0) {
            GLuint uvHandle = 0;
            glEnableVertexAttribArray(2);
            glGenBuffers(1, &uvHandle);
            glBindBuffer(GL_ARRAY_BUFFER, uvHandle);
            glBufferData(GL_ARRAY_BUFFER, mesh->texcoords.size() * sizeof(float), &mesh->texcoords[0], GL_STATIC_DRAW);
            glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 0, 0);
            uvHandles.push_back(uvHandle);
        }
        else {
            uvHandles.push_back(0);
        }

        GLuint indicesHandle = -1;
        glEnableVertexAttribArray(3);
        glGenBuffers(1, &indicesHandle);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesHandle);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, mesh->indices.size() * sizeof(unsigned int), &mesh->indices[0], GL_STATIC_DRAW);
        glVertexAttribPointer(3, 3, GL_FLOAT, GL_FALSE, 0, 0);
        indicesHandles.push_back(indicesHandle);

        meshSizes.push_back(mesh->indices.size());

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        loadTexture(&materials[shapes[i].mesh.material_ids[0]], i);

        mesh = NULL;
    }
    totalShapes = shapes.size();
    shapes.clear();
    std::cout << "printing" << std::endl;
}

void Mesh::loadTexture(tinyobj::material_t *materials, int spot)
{

    int width, height;
    textures.push_back(0);
    char *image = ImageLoader::loadTGA(materials->diffuse_texname.c_str(), width, height);
    glGenTextures(1, &this->textures[spot]);
    glBindTexture(GL_TEXTURE_2D, this->textures[spot]);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, &image[0]);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);
    glGenerateMipmap(GL_TEXTURE_2D);

}

void Mesh::draw()
{
    glEnable(GL_TEXTURE_2D);
    for (int i = 0; i < totalShapes; i++) {
        glBindTexture(GL_TEXTURE_2D, textures[i]);

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandles[i]);
        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
        if (normalsHandles.size() > i) {
            if (normalsHandles[i] != 0) {
                glEnableVertexAttribArray(1);
                glBindBuffer(GL_ARRAY_BUFFER, normalsHandles[i]);
                glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, 0);
            }
        }
        if (uvHandles.size() > i) {
            std::cout << uvHandles[i] << std::endl;
            glEnableVertexAttribArray(2);
            glBindBuffer(GL_ARRAY_BUFFER, uvHandles[i]);
            glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 0, 0);
        }
        glEnableVertexAttribArray(3);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesHandles[i]);
        glVertexAttribPointer(3, 3, GL_FLOAT, GL_FALSE, 0, 0);
        glDrawElements(GL_TRIANGLE_STRIP, meshSizes[i], GL_UNSIGNED_INT, (void *) 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
    }
    glBindTexture(GL_TEXTURE_2D, 0);
    glDisable(GL_TEXTURE_2D);
}