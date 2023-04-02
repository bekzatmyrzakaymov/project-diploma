package com.diploma.project.exception.handler;

import lombok.Builder;

@Builder
public class ExceptionBody {

    public String type;
    public String code;
    public String message;
    public String path;
}
