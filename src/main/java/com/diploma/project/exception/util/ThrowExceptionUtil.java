package com.diploma.project.exception.util;

import com.diploma.project.exception.CustomException;
import com.diploma.project.exception.constants.ExceptionConstants;
import java.util.function.Supplier;

public class ThrowExceptionUtil {

    public static Supplier<CustomException> throwCustomExceptionByCodeNF012(String id, Class clazz) {
        String message = ExceptionConstants.NF012.getTemplateMessage();
        final String formatMessage = String.format(message, clazz.getSimpleName(), id);
        return () -> new CustomException(ExceptionConstants.NF012, formatMessage);
    }

    public static void throwCustomExceptionByCodeISE01(String fileType, String exceptionMessage) {
        String message = ExceptionConstants.ISE01.getTemplateMessage();
        final String formatMessage = String.format(message, fileType, exceptionMessage);
        throw new CustomException(ExceptionConstants.ISE01, formatMessage);
    }

    public static void throwCustomExceptionByCodeCPE019() {
        String message = ExceptionConstants.CPE19.getTemplateMessage();
        throw new CustomException(ExceptionConstants.CPE19, message);
    }

    public static void throwCustomExceptionByCodeIU004() {
        String message = ExceptionConstants.IU004.getTemplateMessage();
        throw new CustomException(ExceptionConstants.IU004, message);
    }
}
