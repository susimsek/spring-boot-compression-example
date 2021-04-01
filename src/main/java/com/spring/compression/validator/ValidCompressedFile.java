package com.spring.compression.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CompressedFileValidator.class})
public @interface ValidCompressedFile {
    String message() default "{compress.file.not.valid}";

    @NotBlank
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}