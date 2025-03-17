package iuh.fit.se.notificationservice.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;


public interface FileService {

    void createDirectory(String folder) throws URISyntaxException;

    String store(MultipartFile file, String folder) throws URISyntaxException, IOException;

    long getFileLength(String fileName, String folder) throws URISyntaxException;

    String createNestedDirectory(String folder) throws IOException, URISyntaxException;

    InputStreamResource getResource(String fileName, String folder)
            throws URISyntaxException, FileNotFoundException;
}
