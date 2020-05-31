package com.dictionary.server.library.model.converter;

import com.dictionary.server.library.exception.LibraryException;
import org.apache.commons.io.FilenameUtils;

class FileExtensionDeterminator {
    static String determineBy(String fileName) {
        String fileExtension = FilenameUtils.getExtension(fileName);
        if("txt".equals(fileExtension) || "epub".equals(fileExtension)) {
            return fileExtension;
        } else {
            throw new LibraryException("[FileExtensionDeterminator]: cannot determine file extension by fileName: " + fileName);
        }
    }
}
