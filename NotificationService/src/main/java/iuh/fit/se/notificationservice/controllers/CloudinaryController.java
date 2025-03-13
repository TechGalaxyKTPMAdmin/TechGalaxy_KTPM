package iuh.fit.se.notificationservice.controllers;

import iuh.fit.se.notificationservice.dto.response.DataResponse;
import iuh.fit.se.notificationservice.dto.response.UploadFileResponse;
import iuh.fit.se.notificationservice.exception.AppException;
import iuh.fit.se.notificationservice.exception.ErrorCode;
import iuh.fit.se.notificationservice.services.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }


    @PostMapping("/file")
    @ResponseBody
    public ResponseEntity<DataResponse<UploadFileResponse>> upload(@RequestParam MultipartFile file) throws IOException {
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

        BufferedImage bi = ImageIO.read(file.getInputStream());
        if (bi == null) {
            return ResponseEntity.badRequest().body(DataResponse.<UploadFileResponse>builder().message("Invalid image").build());
        }
        Map result = cloudinaryService.upload(file);
        System.out.println(result.get("original_filename"));
        System.out.println(result.get("url"));
        System.out.println(result.get("public_id"));

        UploadFileResponse res = new UploadFileResponse(result.get("url").toString(), Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(DataResponse.<UploadFileResponse>builder().message("Upload file success")
                .data(Arrays.asList(res)).build());

    }

    // Upload multiple files
    @PostMapping("/files")
    public ResponseEntity<DataResponse<List<UploadFileResponse>>> uploadMultiple(
            @RequestParam(name = "files", required = false) MultipartFile[] files
    ) throws URISyntaxException, IOException {
        if (files == null || files.length == 0) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }

        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "svg");
        List<UploadFileResponse> responses = new ArrayList<>();
        List<String> errors = new ArrayList<>();


        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                errors.add("File is empty: " + file.getOriginalFilename());
                continue;
            }
            BufferedImage bi = ImageIO.read(file.getInputStream());
            if (bi == null) {
                errors.add("Invalid image: " + file.getOriginalFilename());
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

            Map result = cloudinaryService.upload(file);
            System.out.println(result.get("original_filename"));
            System.out.println(result.get("url"));
            System.out.println(result.get("public_id"));

            UploadFileResponse res = new UploadFileResponse(result.get("url").toString(), Instant.now());
            responses.add(res);
        }

        return ResponseEntity.ok(DataResponse.<List<UploadFileResponse>>builder()
                .data(List.of(responses))
                .build());
    }

}