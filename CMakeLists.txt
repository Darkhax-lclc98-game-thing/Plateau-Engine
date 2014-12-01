set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "E:\\Documents\\GitHub\\Plateau-Engine (copy)\\bin")
set(CMAKE_CXX_FLAGS -DGLEW_STATIC)
set(CMAKE_CXX_STANDARD_LIBRARIES "${CMAKE_CXX_STANDARD_LIBRARIES} -lglew32s -lglfw3 -lopengl32 -lglu32  -lwinmm ")

cmake_minimum_required(VERSION 2.8.12.2)

file(GLOB ConfigFiles ${CMAKE_SOURCE_DIR}/config/*.json
                      ${CMAKE_SOURCE_DIR}/config/*.conf)

add_custom_target(copy)
foreach(ConfigFile ${ConfigFiles})
  add_custom_command(TARGET copy PRE_BUILD
                     COMMAND ${CMAKE_COMMAND} -E
                         copy ${ConfigFile} ${CMAKE_RUNTIME_OUTPUT_DIRECTORY})
endforeach()

file(GLOB_RECURSE SOURCE_FILES "src/*.cpp")

add_executable(Game ${SOURCE_FILES})