#include <jni.h>
#include <string>
#include "art_method_9_0_0.h"

// 这个函数没用，创建C++项目时AS自动生成的示例
extern "C" JNIEXPORT jstring JNICALL
Java_com_yu_myfix_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT void JNICALL
Java_com_yu_myfix_FixManager_replace(JNIEnv *env, jobject instance, jobject wrongMethod,
                                     jobject rightMethod) {

    art::ArtMethod *wrong=  (art::ArtMethod *)env->FromReflectedMethod(wrongMethod);
    art::ArtMethod *right=  (art::ArtMethod *)env->FromReflectedMethod(rightMethod);

    wrong->declaring_class_ = right->declaring_class_;
    wrong->ptr_sized_fields_.data_ = right->ptr_sized_fields_.data_;
    wrong->access_flags_ = right->access_flags_;
    wrong->ptr_sized_fields_.entry_point_from_quick_compiled_code_ = right->ptr_sized_fields_.entry_point_from_quick_compiled_code_;
    wrong->dex_code_item_offset_ = right->dex_code_item_offset_;
    wrong->method_index_ = right->method_index_;
    wrong->dex_method_index_ = right->dex_method_index_;

}