package com.dictionary.server.manager.file;

import com.dictionary.server.library.exception.LibraryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Component
@Slf4j
public class FileManagerImpl implements FileManager {

    @Value("${library.path-to-file-storage}")
    private String FILE_LIBRARY_PATH;

    @Value("${public-library.path-to-file-storage}")
    private String PUBLIC_LIBRARY_PATH;

    @Override
    public String saveFileForLibrary(MultipartFile file, String fileName) {
        final FileExtension fileExtension = FileHelper.getFileExtension(file);

        final String fullFileName = fileName + "." + fileExtension.getValue();

        File newFile = new File(FILE_LIBRARY_PATH + fullFileName);

//        newFile.getParentFile().mkdirs(); // creating dirs if they are not exist.
//        newFile.createNewFile();

        try {
            file.transferTo(newFile);
            log.info("File was saved: " + fullFileName);
        } catch (IOException e) {
            throw new FileManagerException("Cannot save file for library: " + fullFileName);
        }

        return fullFileName;
    }

    @Override
    public void createOutputStreamForUserLibrary(HttpServletResponse response, String filename) {
        createOutputStream(response, FILE_LIBRARY_PATH, filename);
    }

    @Override
    public void createOutputStreamForPublicLibrary(HttpServletResponse response, String filename) {
        createOutputStream(response, PUBLIC_LIBRARY_PATH, filename);
    }

    private void createOutputStream(HttpServletResponse response, final String storagePath, final String filename) {
        try {
            File file = new File(storagePath + filename);
            file.getParentFile().mkdirs(); // creating dirs if they are not exist.
//        newFile.createNewFile();
            String contentType = file.toURI().toURL().openConnection().getContentType();

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
            inStream.close();
        } catch (Exception e) {
            throw new LibraryException("Cannot download the book: " + e);
        }
    }

    @Override
    public boolean deleteFileForLibrary(String fileName) {
        File file = new File(FILE_LIBRARY_PATH + fileName);
        return file.delete();
    }
}
