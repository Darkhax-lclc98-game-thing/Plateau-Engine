#include "ImageLoader.h"

GLubyte* ImageLoader::loadBmp(char *name, const int w, const int h)
{
    // Load data
    GLubyte *tmp = new GLubyte[4 * w * h];
    memset(tmp, 0, 4 * w * h * sizeof(GLubyte));
    FILE *fp;
    fp = fopen(name, "rb");
    for (int i = 0; i < 4 * w * h; i += 4) {
        fread(&tmp[i], sizeof(GLubyte), 3, fp);
        tmp[i + 3] = 255;
    }
    fclose(fp);

    // Invert texture
    GLubyte *data = new GLubyte[4 * w * h];
    memset(data, 0, 4 * w * h * sizeof(GLubyte));
    for (int i = 0; i < h; ++i) {
        memcpy(&data[4 * w * (h - i - 1)], &tmp[4 * w * i], 4 * w);
    }

    delete[] tmp;
    return data;
}