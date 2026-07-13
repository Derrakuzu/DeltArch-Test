#include <jni.h>
#include <string.h>

// Fixed-size global buffers for core directories
char g_system_dir[1024];
char g_save_dir[1024];

extern "C" JNIEXPORT void JNICALL
Java_com_daskoul_deltarch_core_NativeBridge_initializeDirs(
    JNIEnv *env,
    jobject thiz,
    jstring j_system_dir,
    jstring j_save_dir
) {
    const char *sd = env->GetStringUTFChars(j_system_dir, nullptr);
    const char *sv = env->GetStringUTFChars(j_save_dir, nullptr);

    // Validate lengths to prevent silent truncation and abort if the path
    // equals or exceeds the maximum capacity of the target destination buffers
    if (strlen(sd) >= sizeof(g_system_dir) || strlen(sv) >= sizeof(g_save_dir)) {
        env->ReleaseStringUTFChars(j_system_dir, sd);
        env->ReleaseStringUTFChars(j_save_dir, sv);

        jclass exClass = env->FindClass("java/lang/IllegalArgumentException");
        if (exClass != nullptr) {
            env->ThrowNew(exClass, "Directory path length exceeds maximum native buffer capacity.");
        }
        return;
    }

    // Securely copy using strlcpy, which guarantees null-termination
    // within the bounds of the destination buffer size
    strlcpy(g_system_dir, sd, sizeof(g_system_dir));
    strlcpy(g_save_dir, sv, sizeof(g_save_dir));

    env->ReleaseStringUTFChars(j_system_dir, sd);
    env->ReleaseStringUTFChars(j_save_dir, sv);
}
