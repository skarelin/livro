package com.dictionary.server.manager.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

abstract class FileHelper {
    static FileExtension getFileExtension(MultipartFile file) {
        return FileExtension.fromString(FilenameUtils.getExtension(file.getOriginalFilename()));
    }
}
