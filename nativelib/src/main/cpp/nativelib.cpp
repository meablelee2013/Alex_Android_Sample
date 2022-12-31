#include <jni.h>
#include <string>

// 日志输出
#include <android/log.h>

#define TAG "Alex"

// __VA_ARGS__ 代表 ...的可变参数
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG,  __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG,  __VA_ARGS__);
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG,  __VA_ARGS__);


extern "C" JNIEXPORT jstring JNICALL
Java_com_alex_nativelib_NativeLib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_alex_nativelib_NativeLib_addInt(JNIEnv *env, jobject thiz, jint x, jint y) {
    LOGD("_count:%d \n",x)
    return x + y;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_alex_nativelib_NativeLib_testArrayAction(JNIEnv *env, jobject thiz, jint count, jstring text_info, jintArray ints, jobjectArray strs) {

}