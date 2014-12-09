#ifndef IMAGELOADER_H
#define IMAGELOADER_H

#include <GL/glew.h>
#include <GL/glu.h>
#include <fstream>
#include <stdlib.h>
#include <windef.h>
#include <iostream>

class ImageLoader
{
private:
#pragma pack(push,1)
    typedef struct
    {
        char rgbBlue;
        char rgbGreen;
        char rgbRed;
        char rgbReserved;
    } RGBQuad;

    typedef struct
    {
        unsigned short bfType;
        unsigned int bfSize;
        unsigned short bfReserved1;
        unsigned short bfReserved2;
        unsigned int bfOffBits;
    } BitmapFileHeader;

    typedef struct
    {
        unsigned int biSize;
        int biWidth;
        int biHeight;
        unsigned short biPlanes;
        unsigned short biBitCount;
        unsigned int biCompression;
        unsigned int biSizeImage;
        int biXPelsPerMeter;
        int biYPelsPerMeter;
        unsigned int biClrUsed;
        unsigned int biClrImportant;
    } BitmapInfoHeader;
#pragma pack(pop)

    BitmapFileHeader bmfh;
    BitmapInfoHeader bmih;
    int byteWidth;
    int padWidth;
    unsigned int dataSize;

    char *convert24(char *data);

public:

    GLuint loadBMP(char const *name);


    RGBQuad *colours;
    int width, height;
    unsigned short bpp;
};

#endif