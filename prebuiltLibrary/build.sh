lib="knlib"
rm -Rf build
mkdir build
konanc -p dynamic -target android_arm32 src/main/kotlin/main.kt -o build/$lib
mkdir build/include
mkdir build/libs
mkdir build/libs/armeabi-v7a
mv build/${lib}_api.h build/include/${lib}.h
mv build/lib${lib}.so build/libs/armeabi-v7a/