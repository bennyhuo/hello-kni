import platform.android.*
import kotlinx.cinterop.*
import kotlin.native.CName

//FIXME C_PACKAGE_NAME &  C_NAME_PREFIX can use  buildkonfig-gradle-plugin ?
const val C_PACKAGE_NAME: String = "com/example/hellojni/HelloJni"
const val C_NAME_PREFIX: String = "Java_com_example_hellojni_HelloJni" //FIXME How use C_NAME_PREFIX_KCONFIG ?

@CName(C_NAME_PREFIX+"_sayHello")
fun sayHello() {
    __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "Hello %s", "Native")
}

@CName(C_NAME_PREFIX+"_stringFromJNI")
fun stringFromJNI(env: CPointer<JNIEnvVar>, thiz: jobject): jstring {
    memScoped {
        return env.pointed.pointed!!.NewStringUTF!!.invoke(env, "This is from Kotlin Native!!".cstr.ptr)!!
    }
}

@CName(C_NAME_PREFIX+"_callJava")
fun callJava(env: CPointer<JNIEnvVar>, thiz: jobject): jstring {
    memScoped {
        val jniEnvVal = env.pointed.pointed!!
        val jclass = jniEnvVal.GetObjectClass!!.invoke(env, thiz)
        val methodId = jniEnvVal.GetMethodID!!.invoke(
            env, jclass,
            "callFromNative".cstr.ptr, "()Ljava/lang/String;".cstr.ptr
        )
        return jniEnvVal.CallObjectMethodA!!.invoke(env, thiz, methodId, null) as jstring
    }
}

fun sayHello2() {
    __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "Hello %s", "Native")
}

@CName("JNI_OnLoad")
fun JNI_OnLoad(vm: CPointer<JavaVMVar>, preserved: COpaquePointer): jint {
    return memScoped {
        val envStorage = alloc<CPointerVar<JNIEnvVar>>()
        val vmValue = vm.pointed.pointed!!
        val result = vmValue.GetEnv!!(vm, envStorage.ptr.reinterpret(), JNI_VERSION_1_6)
        __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "JNI_OnLoad")
        if (result == JNI_OK) {
            val env = envStorage.pointed!!.pointed!!
            val jclass = env.FindClass!!(envStorage.value, C_PACKAGE_NAME.cstr.ptr)

            val jniMethod = allocArray<JNINativeMethod>(1)
            jniMethod[0].fnPtr = staticCFunction(::sayHello2)
            jniMethod[0].name = "sayHello2".cstr.ptr
            jniMethod[0].signature = "()V".cstr.ptr
            env.RegisterNatives!!(envStorage.value, jclass, jniMethod, 1)

            __android_log_print(
                ANDROID_LOG_INFO.toInt(),
                "Kn",
                "register say hello2, %d, %d",
                sizeOf<CPointerVar<JNINativeMethod>>(),
                sizeOf<JNINativeMethod>()
            )
        }
        JNI_VERSION_1_6
    }
}
