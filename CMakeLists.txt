set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "E:\\Documents\\GitHub\\Plateau-Engine (copy)\\bin")
list(APPEND CMAKE_CXX_FLAGS "-std=c++0x -DGLEW_STATIC")
set(CMAKE_CXX_STANDARD_LIBRARIES "${CMAKE_CXX_STANDARD_LIBRARIES} -lglew32s -lglfw3 -lglut32 -lopengl32 -lglu32  -lwinmm ")

cmake_minimum_required(VERSION 2.8.12.2)

file(GLOB_RECURSE SOURCE_FILES "src/*.cpp")

add_executable(Game ${SOURCE_FILES})