
namespace art {
    class ArtMethod {
    public:

        // Field order required by test "ValidateFieldOrderOfJavaCppUnionClasses".
        // The class we are a part of.
        uint32_t declaring_class_;
        // Access flags; low 16 bits are defined by spec.
        uint32_t access_flags_;
        /* Dex file fields. The defining dex file is available via declaring_class_->dex_cache_ */
        // Offset to the CodeItem.
        uint32_t dex_code_item_offset_;
        // Index into method_ids of the dex file associated with this method.
        uint32_t dex_method_index_;
        /* End of dex file fields. */
        // Entry within a dispatch table for this method. For static/direct methods the index is into
        // the declaringClass.directMethods, for virtual methods the vtable and for interface methods the
        // ifTable.
        uint16_t method_index_;

        // The hotness we measure for this method. Incremented by the interpreter. Not atomic, as we allow
        // missing increments: if the method is hot, we will see it eventually.
        uint16_t hotness_count_;
        // Fake padding field gets inserted here.
        // Must be the last fields in the method.
        // PACKED(4) is necessary for the correctness of
        // RoundUp(OFFSETOF_MEMBER(ArtMethod, ptr_sized_fields_), pointer_size).
        struct PtrSizedFields {
            // Depending on the method type, the data is
            //   - native method: pointer to the JNI function registered to this method
            //                    or a function to resolve the JNI function,
            //   - conflict method: ImtConflictTable,
            //   - abstract/interface method: the single-implementation if any,
            //   - proxy method: the original interface method or constructor,
            //   - other methods: the profiling data.
            void* data_;

            // Method dispatch from quick compiled code invokes this pointer which may cause bridging into
            // the interpreter.
            void* entry_point_from_quick_compiled_code_;
        } ptr_sized_fields_;

    };
}
