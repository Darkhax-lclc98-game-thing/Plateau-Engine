#include "Opengl.h"

class Entity {
public:
    Entity();

    ~Entity();

    float x, y, z, yaw, pitch, roll, motionX, motionY, motionZ;

    void update();

    float getX();
    float getY() ;
    float getZ() ;

    float getYaw();
    float getPitch();
    float getRoll();

    float getMotionX();
    float getMotionY() ;
    float getMotionZ();

    void setX(float x1);
    void setY(float y1);
    void setZ(float z1);

    void setYaw(float yaw1);
    void setPitch(float pitch1);
    void setRoll(float roll1);

    void setMotionX(float motionX1);
    void setMotionY(float motionY1);
    void setMotionZ(float motionZ1);
};
