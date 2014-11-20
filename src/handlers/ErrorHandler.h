#include <GLFW/glfw3.h>
#include <stdio.h>

class ErrorHandler
{
public:
    static void error_callback(int, const char*);
};