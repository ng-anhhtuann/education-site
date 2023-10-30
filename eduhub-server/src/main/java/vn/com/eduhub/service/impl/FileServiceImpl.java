package vn.com.eduhub.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ClassPathResource;
import vn.com.eduhub.dto.master.FileDto;
import vn.com.eduhub.service.IFileService;
import vn.com.eduhub.utils.CommonConstant;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.io.FileOutputStream;
import java.util.Objects;

@Service
@PropertySource("classpath:application.properties")
public class FileServiceImpl implements IFileService {

    @Value("${firebase.bucket-name}")
    private String bucketName;

    @Value("${firebase.key-name}")
    private String path;

    @Value("${firebase.download-url}")
    private String downloadUrl;

    @Value("${firebase.app-name}")
    private String appName;

    private final String IMAGE = "image";
    
    private final String VIDEO = "video";
    
    @Override
    public FileDto uploadImage(MultipartFile multipartFile) throws Exception {

        String fileName = generateUniqueFileName(multipartFile.getOriginalFilename());
        if (!isImageFile(Objects.requireNonNull(multipartFile.getOriginalFilename())))
            throw new Exception(CommonConstant.IMAGE_INVALID);
        try {
            File file = this.convertToFile(multipartFile, fileName);
            String url = this.uploadFile(fileName, file, IMAGE);
            file.delete();
            return new FileDto(fileName, url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }

    @Override
    public FileDto uploadVideo(MultipartFile multipartFile) throws Exception {

        String fileName = generateUniqueFileName(multipartFile.getOriginalFilename());
        if (!isVideoFile(Objects.requireNonNull(multipartFile.getOriginalFilename())))
            throw new Exception(CommonConstant.VIDEO_INVALID);
        try {
            File file = this.convertToFile(multipartFile, fileName);
            String url = this.uploadFile(fileName, file, VIDEO);
            file.delete();
            return new FileDto(fileName, url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }

    @Override
    public boolean deleteFile(String fileName) throws Exception {
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            ClassPathResource serviceAccount = new ClassPathResource(path);

            Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setProjectId(appName).build().getService();
            return storage.delete(blobId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }

    }

    private String generateUniqueFileName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append(name);
        return sb.toString();
    }

    private String uploadFile(String fileName, File file, String type) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(type).build();
        ClassPathResource serviceAccount = new ClassPathResource(path);
        Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setProjectId(appName).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return String.format(downloadUrl, URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8)));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private boolean isImageFile(String fileName) {
        // .jpeg,.jpg,.jpe,.png, .gif, .svg
        // Implement a check for image file extensions
        return fileName.toLowerCase().endsWith(".jpe") || fileName.toLowerCase().endsWith(".jpeg")
                || fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg")
                || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".svg");
    }

    private boolean isVideoFile(String fileName) {
        // Implement a check for video file extensions
        return fileName.toLowerCase().endsWith(".mp4") || fileName.toLowerCase().endsWith(".mov") || fileName.toLowerCase().endsWith(".avi")
                || fileName.toLowerCase().endsWith(".vmw");
    }

}
