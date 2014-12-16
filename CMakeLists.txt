set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/bin")
list(APPEND CMAKE_CXX_FLAGS "-std=c++0x -DGLEW_STATIC -I\"${CMAKE_CURRENT_SOURCE_DIR}/src\"")
IF(WIN32)
    set(CMAKE_CXX_STANDARD_LIBRARIES "${CMAKE_CXX_STANDARD_LIBRARIES} -lglew32s -lglfw3 -lglut32 -lopengl32 -lglu32 -lwinmm")
ELSE()
    set(CMAKE_CXX_STANDARD_LIBRARIES "${CMAKE_CXX_STANDARD_LIBRARIES} -lGLEW -lglfw3 -lX11 -lXxf86vm -lXrandr -lpthread -lXi -lglut -lGL -lGLU")
ENDIF()
cmake_minimum_required(VERSION 2.8.0)

file(GLOB_RECURSE SOURCE_FILES "src/*.cpp")

add_executable(Game ${SOURCE_FILES})