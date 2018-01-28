cmake_minimum_required(VERSION 3.4.1)

set(PREBUILT ${CMAKE_SOURCE_DIR}/../../../../prebuiltLibrary/build)

add_library(hello-jni SHARED
            hello-jni.c)

target_include_directories(hello-jni PRIVATE
                      ${PREBUILT}/include)

add_library(libknlib SHARED IMPORTED)
set_target_properties(libknlib PROPERTIES
                    IMPORTED_LOCATION "${PREBUILT}/libs/armeabi-v7a/libknlib.so"
                    IMPORTED_NO_SONAME ON)

# Include libraries needed for hello-jni lib
target_link_libraries(hello-jni
                      android
                      log
                      libknlib)
