#include "RenderHandler.h"

EntityPlayer player;
World world;
extern Config config;

void perspectiveGL(GLdouble fovY, GLdouble aspect, GLdouble zNear, GLdouble zFar)
{
    GLdouble fW, fH;
    fH = tan(fovY / 360 * 3.1415926535897932384626433832795) * zNear;
    fW = fH * aspect;
    glFrustum(-fW, fW, -fH, fH, zNear, zFar);
}

GLuint test;

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
GLuint testz;

std::string NumberToString(float num)
{
    std::stringstream ss;
    ss << num;
    return ss.str();
}
void loadFromMesh(std::vector<tinyobj::shape_t> shapes) {
    for (size_t i = 0; i < shapes.size(); ++i) {
        glBegin(GL_TRIANGLES);
        std::vector<unsigned int> indices = shapes[i].mesh.indices;
        for (size_t f = 0; f < indices.size(); ++f) {
            glVertex3f(shapes[i].mesh.positions[indices[f] * 3 + 0],
                    shapes[i].mesh.positions[indices[f] * 3 + 1],
                    shapes[i].mesh.positions[indices[f] * 3 + 2]);
        }
        glEnd();
    }
}

void RenderHandler::loadModel()
{
    std::string inputfile = "bridge.obj";
    tinyobj::LoadObj(shapes, materials, inputfile.c_str());
    test = ImageLoader::loadBMP("surface.bmp");
    testz = glGenLists(0);
    glNewList(testz, GL_COMPILE);
    loadFromMesh(shapes);
    glEndList();
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
    world.create("heightField.raw", 1024, 1024);

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

    glPushMatrix();
    glColor4f(1, 1, 1, 1);
    glCallList(testz);
    glPopMatrix();

    // glPushMatrix();
    // world.render();
    // glPopMatrix();
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