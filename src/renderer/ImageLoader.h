#ifndef IMAGELOADER_H
#define IMAGELOADER_H
#include <GL/gl.h>
#include <string.h>
#include <stdio.h>

class ImageLoader
{
public:
    GLubyte *loadBmp(char *name, const int w, const int h);

};

#endif