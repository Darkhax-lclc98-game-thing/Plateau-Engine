#include "ImageLoader.h"

GLuint ImageLoader::loadBMP(char const *name)
{
    GLuint texture;
    FILE *in;
    char *tempData;

    if (colours != 0) {
        delete[] colours;
    }

    in = fopen(name, "rb");

    if (in == NULL) {
        fclose(in);
        return 0;
    }

    fread(&bmfh, sizeof(BitmapFileHeader), 1, in);
    fread(&bmih, sizeof(BitmapInfoHeader), 1, in);
    width = bmih.biWidth;
    height = bmih.biHeight;
    bpp = bmih.biBitCount;
    dataSize = (width * height * (unsigned int) (bmih.biBitCount / 8.0));

    tempData = new char[dataSize];
    fread(tempData, sizeof(char), dataSize, in);
    fclose(in);

    byteWidth = padWidth = (int) ((float) width * (float) bpp / 8.0);

    while (padWidth % 4 != 0) {
        padWidth++;
    }

    tempData = convert24(tempData);

    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, tempData);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

    free(tempData);
    return texture;
}

char *ImageLoader::convert24(char *tempData)
{
    char *data = new char[width * height * 3];

    int offset = padWidth - byteWidth;
    for (int i = 0; i < dataSize; i += 3) {
        if ((i + 1) % padWidth == 0) {
            i += offset;
        }
        *(data + i + 2) = *(tempData + i);
        *(data + i + 1) = *(tempData + i + 1);
        *(data + i) = *(tempData + i + 2);
    }

    return data;
}

