package iuh.fit.se.notificationservice.services.impl;

import iuh.fit.se.notificationservice.exception.AppException;
import iuh.fit.se.notificationservice.exception.ErrorCode;
import iuh.fit.se.notificationservice.services.FileService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload-file.base-uri}")
    private String uploadDir;
    private String baseURI;

    @PostConstruct
    public void init() {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        this.baseURI = uploadPath.toString();
        this.baseURI = this.baseURI.replace("\\", "/");
        this.baseURI = "file:///" + this.baseURI + "/";
        this.baseURI = this.baseURI.replace(" ", "%20");

    }

    @Override
    public void createDirectory(String folder) throws URISyntaxException {
        folder = folder.replace(" ", "%20");
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectory(tmpDir.toPath());
            } catch (IOException e) {
                throw new AppException(ErrorCode.CREATE_DIRECTORY_FAILED);
            }
        } else {
            System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
        }
    }

    public String createNestedDirectory(String folder) throws IOException, URISyntaxException {
//        Xóa đi phần đầu chuỗi là base URI
        folder = folder.replace(" ", "%20");
        folder = folder.substring(baseURI.length());
        String[] folders = folder.split("/");
        StringBuilder currentPath = new StringBuilder(baseURI);

        for (String subFolder : folders) {
            currentPath.append(subFolder).append("/");
            createDirectory(currentPath.toString());
        }

        return currentPath.toString();
    }


    @Override
    public String store(MultipartFile file, String folder) throws URISyntaxException, IOException {
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        URI uri = new URI(baseURI + folder + "/" + finalName);
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return finalName;
    }

    @Override
    public long getFileLength(String fileName, String folder) throws URISyntaxException {
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);

        File tmpDir = new File(path.toString());

        if (!tmpDir.exists() || tmpDir.isDirectory())
            return 0;
        return tmpDir.length();
    }

    @Override
    public InputStreamResource getResource(String fileName, String folder) throws URISyntaxException, FileNotFoundException {
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);

        File file = new File(path.toString());
        return new InputStreamResource(new FileInputStream(file));
    }
}
