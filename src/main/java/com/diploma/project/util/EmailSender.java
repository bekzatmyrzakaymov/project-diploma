package com.diploma.project.util;

import com.sun.istack.ByteArrayDataSource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmailSender {

    void send(String to, String email);
    void sendEmailWithSource(String to, String email, ByteArrayDataSource arrayDataSource,String filename);
    void sendEmailWithMultipleSource(String comment, List<String> email, List<MultipartFile> files,ByteArrayDataSource arrayDataSource);
    void sendEmailWithZipFile(List<String> email,String path);
}
