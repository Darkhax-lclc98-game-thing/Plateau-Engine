#include "Mesh.h"

Mesh::Mesh(const char *filename)
{
    std::vector<tinyobj::shape_t> shapes;
    std::vector<tinyobj::material_t> materials;

    tinyobj::LoadObj(shapes, materials, filename);


    for (int i = 0; i < shapes.size(); i += 1) {
        tinyobj::mesh_t *mesh = &shapes[i].mesh;

        glEnableVertexAttribArray(0);
        unsigned int verticesHandle = 0;
        glGenBuffers(1, &verticesHandle);
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandle);
        glBufferData(GL_ARRAY_BUFFER, mesh->positions.size() * sizeof(float), &mesh->positions[0], GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
        this->verticesHandles.push_back(verticesHandle);

        if (mesh->normals.size() > 0) {
            unsigned int normalsHandle = 0;
            glEnableVertexAttribArray(1);
            glGenBuffers(1, &normalsHandle);
            glBindBuffer(GL_ARRAY_BUFFER, normalsHandle);
            glBufferData(GL_ARRAY_BUFFER, mesh->normals.size() * sizeof(float), &mesh->normals[0], GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, 0);
            this->normalsHandles.push_back(normalsHandle);
        }
        else {
            this->normalsHandles.push_back(0);
        }

        if (mesh->texcoords.size() > 0) {
            unsigned int uvHandle = 0;
            glEnableVertexAttribArray(2);
            glGenBuffers(1, &uvHandle);
            glBindBuffer(GL_ARRAY_BUFFER, uvHandle);
            glBufferData(GL_ARRAY_BUFFER, mesh->texcoords.size() * sizeof(float), &mesh->texcoords[0], GL_STATIC_DRAW);
            glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 0, 0);
            this->uvHandles.push_back(uvHandle);
        }
        else {
            this->uvHandles.push_back(0);
        }

        unsigned int indicesHandle = 0;
        glEnableVertexAttribArray(3);
        glGenBuffers(1, &indicesHandle);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesHandle);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, mesh->indices.size() * sizeof(unsigned int), &mesh->indices[0], GL_STATIC_DRAW);
        glVertexAttribPointer(3, 3, GL_FLOAT, GL_FALSE, 0, 0);
        this->indicesHandles.push_back(indicesHandle);

        this->meshSizes.push_back(mesh->indices.size());

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);

        this->loadTexture(&materials[mesh->material_ids[0]], i);

        mesh = NULL;
    }
    this->totalShapes = shapes.size();
    shapes.clear();
    std::cout << "printing" << std::endl;
}

void Mesh::loadTexture(tinyobj::material_t *materials, int spot)
{
    textures.push_back(0);
    ImageLoader loader;
    // Auto detect file extension
    this->textures[spot] = loader.loadBMP(materials->diffuse_texname.c_str());

}

void Mesh::draw()
{
    glEnable(GL_TEXTURE_2D);
    for (int i = 0; i < this->totalShapes; i += 1) {
        glBindTexture(GL_TEXTURE_2D, this->textures[i]);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, this->verticesHandles[i]);
        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
        if (this->normalsHandles.size() > i) {
            if (this->normalsHandles[i] != 0) {
                glEnableVertexAttribArray(1);
                glBindBuffer(GL_ARRAY_BUFFER, this->normalsHandles[i]);
                glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, 0);
            }
        }
        if (this->uvHandles.size() > i) {
            if (this->uvHandles[i] != 0) {
                glEnableVertexAttribArray(2);
                glBindBuffer(GL_ARRAY_BUFFER, this->uvHandles[i]);
                glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 0, 0);
            }
        }
        glEnableVertexAttribArray(3);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this->indicesHandles[i]);
        glVertexAttribPointer(3, 3, GL_FLOAT, GL_FALSE, 0, 0);
        glDrawElements(GL_TRIANGLE_STRIP, this->meshSizes[i], GL_UNSIGNED_INT, (void *) 0);
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