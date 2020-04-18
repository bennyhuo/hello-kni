import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform") version "1.3.71"
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
}

android {
    compileSdkVersion(26)

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(26)

        ndk {
            abiFilters("armeabi-v7a")
        }
    }

    sourceSets {
        val main by getting {
            jniLibs.srcDir(jniLibDir)
        }
    }
}
