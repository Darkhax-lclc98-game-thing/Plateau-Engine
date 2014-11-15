cmake_minimum_required(VERSION 2.8.4)
project(Game)

file(GLOB_RECURSE SOURCE_FILES "src/*.cpp")
file(GLOB_RECURSE HEADERS_FILES "src/*.h")

set (INCLUDE_DIRS "")
foreach (_headerFile ${HEADERS_FILES})
    get_filename_component(_dir ${_headerFile} PATH)
    list (APPEND INCLUDE_DIRS ${_dir})
endforeach()
list(REMOVE_DUPLICATES INCLUDE_DIRS)

include_directories(${INCLUDE_DIRS})

add_executable(Game ${SOURCE_FILES} )