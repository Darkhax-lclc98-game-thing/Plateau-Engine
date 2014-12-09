#include "Entity.h"

Entity::Entity()
{
    x = 0;
    y = 0;
    z = 0;
    yaw = 0;
    pitch = 0;
    roll = 0;
    motionX = 0;
    motionY = 0;
    motionZ = 0;
}

Entity::~Entity()
{

}

void Entity::update()
{
    setX(getX() + getMotionX());
    setY(getY() + getMotionY());
    setZ(getZ() + getMotionZ());

    if (getMotionX() != 0) {
        if (getMotionX() < 0.00001 && getMotionX() > -0.00001) {
            setMotionX(0);
        } else {
            setMotionX(getMotionX() * 0.5f);
        }
    }

    if (getMotionY() != 0) {
        if (getMotionY() < 0.00001 && getMotionY() > -0.00001) {
            setMotionY(0);
        } else {
            setMotionY(getMotionY() * 0.5f);
        }
    }

    if (getMotionZ() != 0) {
        if (getMotionZ() < 0.00001 && getMotionZ() > -0.00001) {
            setMotionZ(0);
        } else {
            setMotionZ(getMotionZ() * 0.5f);
        }
    }
}

float Entity::getX()
{
    return x;
}

float Entity::getY()
{
    return y;
}

float Entity::getZ()
{
    return z;
}

float Entity::getYaw()
{
    return yaw;
}

float Entity::getPitch()
{
    return pitch;
}

float Entity::getRoll()
{
    return roll;
}

float Entity::getMotionX()
{
    return motionX;
}

float Entity::getMotionY()
{
    return motionY;
}

float Entity::getMotionZ()
{
    return motionZ;
}

void Entity::setX(float x1)
{
    x = x1;
}

void Entity::setY(float y1)
{
    y = y1;
}

void Entity::setZ(float z1)
{
    z = z1;
}

void Entity::setYaw(float yaw1)
{
    yaw = yaw1;
}

void Entity::setPitch(float pitch1)
{
    pitch = pitch1;
}

void Entity::setRoll(float roll1)
{
    roll = roll1;
}

void Entity::setMotionX(float motionX1)
{
    motionX = motionX1;
}

void Entity::setMotionY(float motionY1)
{
    motionY = motionY1;
}

void Entity::setMotionZ(float motionZ1)
{
    motionZ = motionZ1;
}
