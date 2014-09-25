#include "Plateau.h"

int main(int argc, char **argv) {
    Plateau* plateau = new Plateau();
    plateau->init(argc, argv);
    return 0;
}

//-lkernel32 -luser32 -lgdi32 -lwinspool -lshell32 -lole32 -loleaut32 -luuid -lcomdlg32 -ladvapi32 -lopengl32 -lglu32 -lglut32