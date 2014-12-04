#include "Config.h"

void generateDefaultJson(GLFWvidmode const *mode)
{
    JsonBox::Object o;

    // options
    o["general"]["language"] = JsonBox::Value("en_us");

    o["mouse"]["mouse acceleration"] = JsonBox::Value(true);
    o["mouse"]["acceleration"] = JsonBox::Value(0);
    o["mouse"]["invert"] = JsonBox::Value(false);
    o["mouse"]["sensitivity"] = JsonBox::Value(50);

    o["interface"]["subtitles"] = JsonBox::Value(true);
    o["interface"]["subtitles language"] = JsonBox::Value("en_us");

    o["video"]["width"] = JsonBox::Value(mode->width);
    o["video"]["refresh"] = JsonBox::Value(mode->refreshRate);
    o["video"]["height"] = JsonBox::Value(mode->height);
    o["video"]["vsync"] = JsonBox::Value(false);
    o["video"]["fov"] = JsonBox::Value(50);
    o["video"]["popin"] = JsonBox::Value(50);

    // keyboard/mouse or gamepad mapping
    o["mapping"]["forward"] = JsonBox::Value(87);
    o["mapping"]["backwards"] = JsonBox::Value(83);
    o["mapping"]["left"] = JsonBox::Value(65);
    o["mapping"]["right"] = JsonBox::Value(68);
    o["mapping"]["pause"] = JsonBox::Value(256);

    o["mapping"]["attack"] = JsonBox::Value(0);


    std::cout << o << std::endl;
    JsonBox::Value v(o);
    v.writeToFile("config.json");

}

void Config::readConfig(GLFWvidmode const *pConst)
{
    WINDOW_WIDTH = 860;
    WINDOW_HEIGHT = 480;
    std::ifstream myfile("config.json");
    if (!myfile.good()) {
        generateDefaultJson(pConst);
    }

    // handle the json and set the variables
    JsonBox::Value v2;
    v2.loadFromStream(myfile);
    WINDOW_WIDTH = v2["video"]["width"].getInt();
    WINDOW_HEIGHT = v2["video"]["height"].getInt();

    VSYNC = v2["video"]["vsync"].getBoolean();

    KEY_FORWARD = v2["mapping"]["forward"].getInt();
    KEY_BACKWARDS = v2["mapping"]["backwards"].getInt();
    KEY_LEFT = v2["mapping"]["left"].getInt();
    KEY_RIGHT = v2["mapping"]["right"].getInt();
    KEY_PAUSE = v2["mapping"]["pause"].getInt();

}

