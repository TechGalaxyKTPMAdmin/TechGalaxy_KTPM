//package iuh.fit.se.notificationservice.controllers;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.MalformedURLException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//@RequestMapping("/storage")
//public class StorageController {
//
//    @Value("${upload-file.base-uri}")
//    private String uploadDir;
//
//    @GetMapping
//    public ResponseEntity<Resource> serveFile(HttpServletRequest request) throws MalformedURLException {
//        String path = request.getRequestURI().replace("/storage/", "");
//        Path filePath = Paths.get(uploadDir).resolve(path).normalize();
//
//        Resource resource = new UrlResource(filePath.toUri());
//        if (!resource.exists()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
//}
