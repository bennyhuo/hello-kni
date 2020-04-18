Hello KNI
=========

This is a Demo of Jni calling Kotlin Native originated from Google's jni demo.

### Contents

Kotlin Native can interop with C directly. In order to call Kotlin Native functions from Java, we should use `CName` to annotated the functions with a proper name:

```kotlin
@CName("Java_com_example_hellojni_HelloJni_stringFromJNI")
fun stringFromJNI(env: CPointer<JNIEnvVar>, thiz: jobject): jstring {
    memScoped {
        return env.pointed.pointed!!.NewStringUTF!!.invoke(env, "This is from Kotlin Native!!".cstr.ptr)!!
    }
}
```

What's in the demo：

* Uses of Android Log
* Return a Java String
* Call Java methods from Kotlin Native

### How to Build


Simply Build and run your app. Dependencies are carefully managed by gradle.

### Preview

Screen shot:

![](images/screen-shot.png)

Logcat :

![](images/log.png)