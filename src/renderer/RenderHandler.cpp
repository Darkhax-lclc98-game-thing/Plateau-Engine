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
}

void RenderHandler::update()
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
    std::cout << player.getX() << " " << player.getY() << " " << player.getZ() << std::endl;
    glPopAttrib();

    glPushMatrix();
    GLfloat vertices[] =
            {
                    -1, -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1,
                    1, -1, -1, 1, -1, 1, 1, 1, 1, 1, 1, -1,
                    -1, -1, -1, -1, -1, 1, 1, -1, 1, 1, -1, -1,
                    -1, 1, -1, -1, 1, 1, 1, 1, 1, 1, 1, -1,
                    -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1, -1,
                    -1, -1, 1, -1, 1, 1, 1, 1, 1, 1, -1, 1
            };

    GLfloat colors[] =
            {
                    0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0,
                    1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0,
                    0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0,
                    0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0,
                    0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0,
                    0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1
            };

    /* We have a color array and a vertex array */
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_COLOR_ARRAY);
    glVertexPointer(3, GL_FLOAT, 0, vertices);
    glColorPointer(3, GL_FLOAT, 0, colors);

    /* Send data : 24 vertices */
    glDrawArrays(GL_QUADS, 0, 24);

    /* Cleanup states */
    glDisableClientState(GL_COLOR_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);
    glPopMatrix();

    glPushMatrix();
    world.render();
    glPopMatrix();
}