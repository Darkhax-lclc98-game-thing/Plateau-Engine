#include "RenderHandler.h"

EntityPlayer player;
World world;
ImageLoader loader;
extern Config config;

void perspectiveGL(GLdouble fovY, GLdouble aspect, GLdouble zNear, GLdouble zFar)
{
    GLdouble fW, fH;
    fH = tan(fovY / 360 * 3.1415926535897932384626433832795) * zNear;
    fW = fH * aspect;
    glFrustum(-fW, fW, -fH, fH, zNear, zFar);
}

static char *readFile(const char *filename)
{
    FILE *fp = fopen(filename, "r");
    fseek(fp, 0, SEEK_END);
    long file_length = ftell(fp);
    fseek(fp, 0, SEEK_SET);
    char *contents = new char[file_length + 1];

    for (int i = 0; i < file_length + 1; i++) {
        contents[i] = 0;
    }
    fread(contents, 1, file_length, fp);
    contents[file_length + 1] = '\0';
    fclose(fp);
    return contents;
}

bool compiledStatus(GLint shaderID)
{
    GLint compiled = 0;
    glGetShaderiv(shaderID, GL_COMPILE_STATUS, &compiled);
    if (compiled) {
        return true;

    } else {
        GLint logLength;
        glGetShaderiv(shaderID, GL_INFO_LOG_LENGTH, &logLength);
        char *msgBuffer = new char[logLength];
        glGetShaderInfoLog(shaderID, logLength, NULL, msgBuffer);
        printf("%s\n", msgBuffer);
        delete (msgBuffer);
        return false;
    }
}

GLuint makeVertexShader(const char *shaderSource)
{
    GLuint vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertexShaderID, 1, (const GLchar **) &shaderSource, NULL);
    glCompileShader(vertexShaderID);
    bool compileCorrectyl = compiledStatus(vertexShaderID);
    if (compileCorrectyl) {
        return vertexShaderID;
    }
    return -1;
}

GLuint makeFragmentShader(const char *shaderSource)
{
    GLuint fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragmentShaderID, 1, (const GLchar **) &shaderSource, NULL);
    glCompileShader(fragmentShaderID);
    bool compileCorrectyl = compiledStatus(fragmentShaderID);
    if (compileCorrectyl) {
        return fragmentShaderID;
    }
    return -1;
}

GLuint makeShaderProgram(GLuint vertexShaderID, GLuint fragmentShaderID)
{
    GLuint shaderID = glCreateProgram();
    glAttachShader(shaderID, vertexShaderID);
    glAttachShader(shaderID, fragmentShaderID);
    glLinkProgram(shaderID);
    return shaderID;
}

std::vector<tinyobj::shape_t> shapes;
std::vector<tinyobj::material_t> materials;
GLuint testz, vertex_buffer, index_buffer, vertex_array_object;

std::string NumberToString(float num)
{
    std::stringstream ss;
    ss << num;
    return ss.str();
}

std::vector<GLuint> testTexture;
GLuint testWorld;

void RenderHandler::loadModel()
{
    std::string inputfile = "sponza.obj";
    tinyobj::LoadObj(shapes, materials, inputfile.c_str());
    // Copy data to GPU
    // Vertex
    size_t vertex_buffer_size = 0;
    for (size_t i = 0; i < shapes.size(); i++) {
        vertex_buffer_size += sizeof(float) * shapes[i].mesh.positions.size();
    }

    glGenBuffers(1, &vertex_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer);
    glBufferData(GL_ARRAY_BUFFER, vertex_buffer_size, NULL, GL_STATIC_DRAW);
    vertex_buffer_size = 0;
    for (size_t i = 0; i < shapes.size(); i++) {
        glBufferSubData(GL_ARRAY_BUFFER, vertex_buffer_size, sizeof(float) * shapes[i].mesh.positions.size(), &shapes[i].mesh.positions[0]);
        vertex_buffer_size += sizeof(float) * shapes[i].mesh.positions.size();
    }
    glBindBuffer(GL_ARRAY_BUFFER, 0);

    // Index
    size_t index_buffer_size = 0;
    for (size_t i = 0; i < shapes.size(); i++) {
        index_buffer_size += sizeof(unsigned int) * shapes[i].mesh.indices.size();
    }

    glGenBuffers(1, &index_buffer);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, index_buffer);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, index_buffer_size, NULL, GL_STATIC_DRAW);
    index_buffer_size = 0;
    for (size_t i = 0; i < shapes.size(); i++) {
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, index_buffer_size, sizeof(unsigned int) * shapes[i].mesh.indices.size(), &shapes[i].mesh.indices[0]);
        index_buffer_size += sizeof(unsigned int) * shapes[i].mesh.indices.size();
    }
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    // draw multiple objects with one draw call
    glGenVertexArrays(1, &vertex_array_object);
    glBindVertexArray(vertex_array_object);
    glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, index_buffer);
    //glBindBuffer(GL_ARRAY_BUFFER, 0);
    //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindVertexArray(0);


//    for (size_t i = 0; i < materials.size(); i++) {
//        testTexture.push_back(ImageLoader::loadtga(materials[i].specular_texname.c_str()));
//    }
}

void RenderHandler::initCamera(int width, int height)
{

    glViewport(0, 0, width, height);

    glClearColor(0, 0.75f, 1, 1);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    perspectiveGL(60, (float) config.WINDOW_WIDTH / config.WINDOW_HEIGHT, 0.1f, 1000);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glEnable(GL_DEPTH_TEST); // Depth Testing
    glDepthFunc(GL_LEQUAL);
    glDisable(GL_CULL_FACE);
    glCullFace(GL_BACK);

    player.setY(25);
    world.hLOD = 8;
    world.create("height.bmp", 4096, 2048);
    testWorld = loader.loadTGA("color.tga");

    loadModel();
}

void RenderHandler::handlePlayer()
{
    player.update();

    glLoadIdentity();
    glPushAttrib(GL_TRANSFORM_BIT);
    glMatrixMode(GL_MODELVIEW);

    glRotatef(-player.getRoll(), 0, 0, 1);

    //if (player.is3rdPerson) {
    //    glTranslatef(0.0F, 0.0F, -player.thirdPersonDistance);
    //}

    glRotatef(player.getPitch(), 1, 0, 0);
    glRotatef(player.getYaw(), 0, 1, 0);

    glTranslated(-player.getX(), -player.getY(), -player.getZ());
    renderText("X: " + NumberToString(player.getX()) + " Y: " + NumberToString(player.getY()) + " Z: " + NumberToString(player.getZ()), GLUT_BITMAP_9_BY_15, 10, 10);
    glPopAttrib();
}

void RenderHandler::handleRender(int fps)
{
    renderText("FPS: " + NumberToString(fps), GLUT_BITMAP_9_BY_15, 10, 25);
    renderText("Shapes: " + NumberToString(shapes.size()), GLUT_BITMAP_9_BY_15, 10, 50);

//    glPushMatrix();
//    glColor4f(1, 1, 1, 1);
//    glEnable(GL_TEXTURE_2D);
//
//    glBindVertexArray(vertex_array_object);
//    glEnableVertexAttribArray(0);
//
//    size_t vertex_buffer_size = 0;
//    for (size_t i = 0; i < shapes.size(); i++) {
//        glBindTexture(GL_TEXTURE_2D, testTexture[shapes[i].mesh.material_ids[0]]);
//        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, (void *) vertex_buffer_size);
//        glDrawElements(GL_LINE_LOOP, sizeof(int) * shapes[i].mesh.indices.size(), GL_UNSIGNED_INT, (void *) 0);
//
//        vertex_buffer_size += sizeof(float) * shapes[i].mesh.positions.size();
//    }
//
//    glDisableVertexAttribArray(0);
//    glBindVertexArray(0);
//    glDisable(GL_TEXTURE_2D);
//    glPopMatrix();

    glPushMatrix();
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, testWorld);
    world.render();
    glDisable(GL_TEXTURE_2D);
    glPopMatrix();
}

void RenderHandler::update(int fps)
{
    handlePlayer();
    handleRender(fps);
}

void RenderHandler::renderText(std::string text, void *font, int x, int y)
{
    glPushMatrix();
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    gluOrtho2D(0, config.WINDOW_WIDTH, 0, config.WINDOW_HEIGHT);
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();
    glEnable(GL_BLEND);
    glBlendFunc(GL_ONE, GL_ONE);

    glRasterPos2i(x, y);
    for (std::string::iterator i = text.begin(); i != text.end(); ++i) {
        char c = *i;
        glColor3d(1.0, 1.0, 1.0);
        glutBitmapCharacter(font, c);
    }

    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
    glDisable(GL_BLEND);
    glPopMatrix();
}