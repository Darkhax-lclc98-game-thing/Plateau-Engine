#include "Config.h"

void generateDefaultJson()
{
    const GLFWvidmode *mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    JsonBox::Object o;

    // options
    o["options"]["mouse"]["mouse acceleration"] = JsonBox::Value(true);
    o["options"]["mouse"]["acceleration"] = JsonBox::Value(0);
    o["options"]["mouse"]["invert look"] = JsonBox::Value(false);
    o["options"]["mouse"]["look sensitivity"] = JsonBox::Value(50);

    o["options"]["interface"]["subtitles"] = JsonBox::Value(true);
    o["options"]["interface"]["subtitles language"] = JsonBox::Value("en_us");

    o["options"]["video"]["width"] = JsonBox::Value(mode->width);
    o["options"]["video"]["height"] = JsonBox::Value(mode->height);
    o["options"]["video"]["vsync"] = JsonBox::Value(false);

    // mapping
    o["options"]["keyboard"]["forward"] = JsonBox::Value(87);
    o["options"]["keyboard"]["backwards"] = JsonBox::Value(83);
    o["options"]["keyboard"]["left"] = JsonBox::Value(65);
    o["options"]["keyboard"]["right"] = JsonBox::Value(68);
    o["options"]["keyboard"]["pause"] = JsonBox::Value(256);

    o["options"]["mouse"]["attack"] = JsonBox::Value(0);


    std::cout << o << std::endl;
    JsonBox::Value v(o);
    v.writeToFile("config.json");

}

void Config::readConfig()
{
    WINDOW_WIDTH = 860;
    WINDOW_HEIGHT = 480;
    std::ifstream myfile("config.json");
   // if (!myfile.good()) {
        generateDefaultJson();
    //}

    // handle the json and set the variables
    JsonBox::Value v2;
    v2.loadFromStream(myfile);
    WINDOW_WIDTH = v2["options"]["video"]["width"].getInt();
    WINDOW_HEIGHT = v2["options"]["video"]["height"].getInt();


}

