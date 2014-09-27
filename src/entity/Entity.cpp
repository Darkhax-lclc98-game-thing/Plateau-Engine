#include "Entity.h"

Entity::Entity() {
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

Entity::~Entity() {
}

void Entity::update() {
    setX(getX() + getMotionX());
}

