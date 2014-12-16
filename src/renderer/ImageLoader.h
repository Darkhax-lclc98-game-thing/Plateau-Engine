#ifndef IMAGELOADER_H
#define IMAGELOADER_H

#include <GL/glew.h>
#include <GL/glu.h>
#include <fstream>
#include <stdlib.h>
#include <iostream>
#include <vector>
#include <stdio.h>
#include <stdexcept>

#include "png/lodepng.h"

class ImageLoader
{
private:
#pragma pack(push,1)
    typedef struct
    {
        char blue;
        char green;
        char red;
        char alpha;
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

    typedef struct
    {
        char idlength;
        char colourmaptype;
        char datatypecode;
        short int colourmaporigin;
        short int colourmaplength;
        char colourmapdepth;
        short int x_origin;
        short int y_origin;
        short width;
        short height;
        char bitsperpixel;
        char imagedescriptor;
    } TGAHeader;

#pragma pack(pop)

public:
    static GLuint loadBMP(char const *name);

    static char * loadTGA(char const *name, int pInt, int i);

    static void loadPNG(char const *name, GLuint textureID);
};

#endif