#ifndef IMAGELOADER_H
#define IMAGELOADER_H

#include <GL/glew.h>
#include <stdlib.h>
#include <GL/glu.h>
#include <wchar.h>
#include <stdio.h>


class ImageLoader
{
private:
public:

    static GLuint loadBMP(char const *name);

};

#endif