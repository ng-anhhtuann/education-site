package vn.com.eduhub.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.eduhub.dto.master.FileDto;
import vn.com.eduhub.exception.BusinessException;
import vn.com.eduhub.exception.ValidationException;
import vn.com.eduhub.service.IFileService;
import vn.com.eduhub.utils.CommonConstant;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    private static final Set<String> IMAGE_EXTENSIONS = Set.of(".jpeg", ".jpg", ".jpe", ".png", ".gif", ".svg");
    private static final Set<String> VIDEO_EXTENSIONS = Set.of(".mp4", ".mov", ".avi", ".wmv");

    @Value("${firebase.bucket-name}")
    private String bucketName;

    @Value("${firebase.key-name}")
    private String keyName;

    @Value("${firebase.download-url}")
    private String downloadUrl;

    @Value("${firebase.app-name}")
    private String appName;

    @Override
    public FileDto uploadImage(MultipartFile multipartFile) {
        String originalName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        if (!hasExtension(originalName, IMAGE_EXTENSIONS)) {
            throw new ValidationException(CommonConstant.IMAGE_INVALID);
        }
        return doUpload(multipartFile, "image");
    }

    @Override
    public FileDto uploadVideo(MultipartFile multipartFile) {
        String originalName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        if (!hasExtension(originalName, VIDEO_EXTENSIONS)) {
            throw new ValidationException(CommonConstant.VIDEO_INVALID);
        }
        return doUpload(multipartFile, "video");
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            Storage storage = buildStorage();
            return storage.delete(blobId);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", fileName, e);
            throw new BusinessException(CommonConstant.PROCESS_FAIL);
        }
    }

    private FileDto doUpload(MultipartFile multipartFile, String contentType) {
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
            Storage storage = buildStorage();
            storage.create(blobInfo, multipartFile.getBytes());
            String url = String.format(downloadUrl, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            log.info("File uploaded: {}", fileName);
            return new FileDto(fileName, url);
        } catch (IOException e) {
            log.error("Failed to upload file: {}", fileName, e);
            throw new BusinessException(CommonConstant.PROCESS_FAIL);
        }
    }

    private Storage buildStorage() throws IOException {
        ClassPathResource serviceAccount = new ClassPathResource(keyName);
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setProjectId(appName)
                .build()
                .getService();
    }

    private boolean hasExtension(String fileName, Set<String> extensions) {
        String lower = fileName.toLowerCase();
        return extensions.stream().anyMatch(lower::endsWith);
    }
}
