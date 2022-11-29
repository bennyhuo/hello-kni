import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform") version "1.7.22"
    id("com.android.library")
}

val jniLibDir = File(project.buildDir, arrayOf("generated", "jniLibs").joinToString(File.separator))

kotlin {
    androidNativeArm32 {
        binaries {
            sharedLib("knlib") {
                if(buildType == NativeBuildType.RELEASE){
                    linkTask.doLast {
                        copy {
                            from(outputFile)
                            into(File(jniLibDir, "armeabi-v7a"))
                        }
                    }

                    afterEvaluate {
                        val preReleaseBuild by tasks.getting
                        preReleaseBuild.dependsOn(linkTask)
                    }
                }
            }
        }
    }

    androidNativeArm64{
        binaries {
            sharedLib("knlib") {
                println("Show name $name")
                println("Show NativeBuildType buildType $buildType")
                // linkTask is default task
                linkTask.doLast {
                    copy {
                        from(outputFile)
                        into(File(jniLibDir, "arm64-v8a"))
                    }
                }

                afterEvaluate {
                    val preReleaseBuild by tasks.getting
                    preReleaseBuild.dependsOn(linkTask)
                }
            }
        }
    }
//    androidNativeX86{}
//    androidNativeX64{}

    //FIXME not gen so !! only blank jar file !! Jvm ? linux ?
//    linuxX64("native") {
//        binaries {
//            sharedLib(namePrefix="knlib") {
//                println("Show name $name")
//                println("Show NativeBuildType buildType $buildType")
//
//                linkTask.doLast {
//                    copy {
//                        from(outputFile)
//                        into(File(jniLibDir, "armeabi-v7a"))
//                    }
//                }
//
//                afterEvaluate {
//                    println("Show task name  ${tasks.getting}")
////                    val preReleaseBuild by tasks.getting//
////                    preReleaseBuild.dependsOn(linkTask)
//                }
//            }
//        }
//    }
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)

        ndk {
//            abiFilters("armeabi-v7a")
            abiFilters += listOf("armeabi-v7a","arm64-v8a" )
        }
    }
    sourceSets.getByName("main") {
            jniLibs.srcDir(jniLibDir)
    }
}
