//
// Created by Casey Idzikowski on 5/21/25.
//

#include <jni.h>
#include <string>
#include <vector>
#include <memory>

/**
 * @brief A helper method to convert jfloatArray from Kotlin to C++ std::shared_ptr<const std::vector<float>>
 * This is used to store the predefined buffer in memory.
 * @param env
 * @param jArray The buffer of floats to be stored in Memory
 * @return std::shared_ptr<const std::vector<float>> A pointer to the buffer of floats to be stored in Memory
 */
std::shared_ptr<const std::vector<float>>
jfloatArrayToSharedVector(JNIEnv *env, jfloatArray jArray) {
    // 1. Get array length
    jsize length = env->GetArrayLength(jArray);

    // 2. Get pointer to array data (copies or pins depending on implementation)
    jfloat *rawArray = env->GetFloatArrayElements(jArray, nullptr);

    // 3. Copy data into a std::vector<float>
    auto vec = std::make_shared<std::vector<float>>(rawArray, rawArray + length);

    // 4. Release the Java array
    env->ReleaseFloatArrayElements(jArray, rawArray, JNI_ABORT); // JNI_ABORT = don't copy back

    // 5. Return as shared_ptr<const vector<float>>
    return std::const_pointer_cast<const std::vector<float>>(vec);
}

/**
 * A helper method to convert jstring from Kotlin to C++ std::string
 * @param env
 * @param jStr
 * @return std::string
 */
inline std::string jstringToStdString(JNIEnv *env, jstring jStr) {
    if (!jStr) return "";

    const char *chars = env->GetStringUTFChars(jStr, nullptr);
    std::string result(chars);
    env->ReleaseStringUTFChars(jStr, chars);

    return result;
}
