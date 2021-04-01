package com.spring.compression.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompressedFileValidator implements ConstraintValidator<ValidCompressedFile, MultipartFile> {

    @Override
    public void initialize(ValidCompressedFile constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        boolean result = true;
        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            result = false;
        }

        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("application/x-7z-compressed")
                || contentType.equals("application/x-rar")
                || contentType.equals("application/zip")
                || contentType.equals("application/x-tar");
    }
}