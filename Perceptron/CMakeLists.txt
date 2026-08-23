cmake_minimum_required(VERSION 3.14)
project(Perceptron C)

set(CMAKE_C_STANDARD 11)
set(CMAKE_C_STANDARD_REQUIRED ON)

include(FetchContent)
FetchContent_Declare(
        raylib
        URL https://github.com/raysan5/raylib/archive/refs/tags/4.5.0.zip
        DOWNLOAD_EXTRACT_TIMESTAMP TRUE
)
set(CMAKE_POLICY_VERSION_MINIMUM 3.5)
FetchContent_MakeAvailable(raylib)

add_executable(Perceptron
        main.c
        data/data.c
        data/ArrayList.c
        draw/draw.c
        logic/logic.c
        data/perceptron.c
        data/node.c
        data/node.h
)

target_link_libraries(Perceptron raylib)