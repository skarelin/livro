package com.dictionary.server.manager.file;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


public interface FileManager {
    String saveFileForLibrary(MultipartFile file, String filename);
    void createOutputStreamForUserLibrary(HttpServletResponse response, String filename);
    void createOutputStreamForPublicLibrary(HttpServletResponse response, String filename);
    boolean deleteFileForLibrary(String filename);
}
