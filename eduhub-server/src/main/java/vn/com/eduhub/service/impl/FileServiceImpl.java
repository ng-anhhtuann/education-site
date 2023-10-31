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
import java.io.IOException;
import java.net.URLEncoder;
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
            byte[] fileBytes = multipartFile.getBytes();
            String url = this.uploadFile(fileName, fileBytes, IMAGE);
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
            byte[] fileBytes = multipartFile.getBytes();
            String url = this.uploadFile(fileName, fileBytes, VIDEO);
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

    private String uploadFile(String fileName, byte[] fileBytes, String type) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(type).build();
        ClassPathResource serviceAccount = new ClassPathResource(path);
        Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setProjectId(appName).build().getService();
        storage.create(blobInfo, fileBytes);

        return String.format(downloadUrl, URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8)));
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
