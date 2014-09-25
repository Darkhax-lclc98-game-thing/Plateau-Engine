#if defined(__APPLE__) || defined(MACOSX)
  #include <GLUT/glut.h>
  #include <OpenGL/gl.h>
  #include <OpenGL/glu.h>
#else

#include <gl/gl.h>
#include <gl/glut.h>

#endif