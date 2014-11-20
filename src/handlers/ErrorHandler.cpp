#include "ErrorHandler.h"

void ErrorHandler::error_callback(int error, const char *description)
{
    fputs(description, stderr);
}