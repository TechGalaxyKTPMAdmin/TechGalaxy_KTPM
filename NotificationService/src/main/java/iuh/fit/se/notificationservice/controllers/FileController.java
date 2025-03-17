package iuh.fit.se.notificationservice.controllers;


import iuh.fit.se.notificationservice.dto.response.DataResponse;
import iuh.fit.se.notificationservice.dto.response.UploadFileResponse;
import iuh.fit.se.notificationservice.exception.AppException;
import iuh.fit.se.notificationservice.exception.ErrorCode;
import iuh.fit.se.notificationservice.services.impl.FileServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FileController {
    @Value("${upload-file.base-uri}")
    private String uploadDir;

    private String baseURI;

    private final FileServiceImpl fileService;

    @Autowired
    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    public void init() {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        this.baseURI = uploadPath.toString();
        this.baseURI =  this.baseURI.replace("\\", "/");
        this.baseURI = "file:///"+ this.baseURI+ "/";
        this.baseURI = this.baseURI.replace(" ", "%20");
    }


    //Upload single file
    @PostMapping("/file")
    public ResponseEntity<DataResponse<UploadFileResponse>> upload(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException {
        // skip validate
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "svg");
        boolean isValid = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));

        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_FILE_EXTENSION);
        }
        if (file.getSize() > 50 * 1024 * 1024) {
            throw new AppException(ErrorCode.FILE_SIZE_EXCEEDED);
        }
        // create a directory if not exist
        folder= folder.replace(" ", "%20");
        this.fileService.createNestedDirectory(baseURI + folder);

        // store file
        String uploadFile = this.fileService.store(file, folder);

        UploadFileResponse res = new UploadFileResponse(uploadFile, Instant.now());

        return ResponseEntity.ok(DataResponse.<UploadFileResponse>builder()
                .data(List.of(res))
                .build());
    }

    // Upload multiple files
    @PostMapping("/files")
    public ResponseEntity<DataResponse<List<UploadFileResponse>>> uploadMultiple(
            @RequestParam(name = "files", required = false) MultipartFile[] files,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException {
        if (files == null || files.length == 0) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }

        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "svg");
        List<UploadFileResponse> responses = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        folder= folder.replace(" ", "%20");

        this.fileService.createNestedDirectory(baseURI + folder);

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                errors.add("File is empty: " + file.getOriginalFilename());
                continue;
            }

            String fileName = file.getOriginalFilename();
            boolean isValid = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));

            if (!isValid) {
                errors.add("Invalid file extension: " + fileName);
                continue;
            }

            if (file.getSize() > 50 * 1024 * 1024) {
                errors.add("File size exceeded for: " + fileName);
                continue;
            }

            String uploadFile = this.fileService.store(file, folder);
            UploadFileResponse res = new UploadFileResponse(uploadFile, Instant.now());
            responses.add(res);
        }

        return ResponseEntity.ok(DataResponse.<List<UploadFileResponse>>builder()
                .data(List.of(responses))
                .build());
    }

}
