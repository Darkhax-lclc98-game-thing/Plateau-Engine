#include "../Opengl.h"

class Entity {
public:
    Entity();

    ~Entity();

    float x, y, z, yaw, pitch, roll, motionX, motionY, motionZ;

    void update();

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getZ() {
        return z;
    }

    float getYaw() {
        return yaw;
    }

    float getPitch() {
        return pitch;
    }

    float getRoll() {
        return roll;
    }

    float getMotionX() {
        return motionX;
    }

    float getMotionY() {
        return motionY;
    }

    float getMotionZ() {
        return motionZ;
    }

    void setX(float x1) {
        x = x1;
    }

    void setY(float y1) {
        y = y1;
    }

    void setZ(float z1) {
        z = z1;
    }

    void setYaw(float yaw1) {
        yaw = yaw1;
    }

    void setPitch(float pitch1) {
        pitch = pitch1;
    }

    void setRoll(float roll1) {
        roll = roll1;
    }

    void setMotionX(float motionX1) {
        motionX = motionX1;
    }

    void setMotionY(float motionY1) {
        motionY = motionY1;
    }

    void setMotionZ(float motionZ1) {
        motionZ = motionZ1;
    }
};
