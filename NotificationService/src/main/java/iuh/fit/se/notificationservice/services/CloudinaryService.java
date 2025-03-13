package iuh.fit.se.notificationservice.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CloudinaryService {
    Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name}")
    private  String cloud_name ;

    @Value("${cloudinary.api-key}")
    private  String api_key ;

    @Value("${cloudinary.api-secret}")
    private String api_secret ;

    @PostConstruct
    public void init() {
        System.out.println("CLOUD_NAME: " + cloud_name);
        System.out.println("API_KEY: " + api_key);
        System.out.println("API_SECRET: " + api_secret);

        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", cloud_name);
        valuesMap.put("api_key", api_key);
        valuesMap.put("api_secret", api_secret);
        cloudinary = new Cloudinary(valuesMap);
    }
    public CloudinaryService() {
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
        return result;
    }

    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
