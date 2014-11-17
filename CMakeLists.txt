set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${Game_SOURCE_DIR}/bin")
set(CMAKE_CXX_STANDARD_LIBRARIES "${CMAKE_CXX_STANDARD_LIBRARIES} -lopengl32 -lglu32 -lglfw3 -lwinmm")

cmake_minimum_required(VERSION 2.8.12.2)

file(GLOB_RECURSE SOURCE_FILES "src/*.cpp")
file(GLOB_RECURSE HEADERS_FILES "src/*.h")

add_executable(Game ${SOURCE_FILES})