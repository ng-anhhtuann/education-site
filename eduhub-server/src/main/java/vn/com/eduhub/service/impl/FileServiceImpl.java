package vn.com.eduhub.service.impl;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.eduhub.dto.master.FileDto;
import vn.com.eduhub.service.IFileService;
import vn.com.eduhub.utils.CommonConstant;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
@PropertySource("classpath:application.properties")
public class FileServiceImpl implements IFileService {

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    @Value("${firebase.bucket-name}")
    private String bucketName;

    @Override
    public FileDto uploadImage(MultipartFile file) throws Exception {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            if (isImageFile(Objects.requireNonNull(file.getOriginalFilename()))) {
                String fileUrl = uploadFile(fileName, file);
                return new FileDto(fileName, fileUrl);
            }
            throw new IllegalArgumentException("Uploaded file is not an image.");
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }

    @Override
    public FileDto uploadVideo(MultipartFile file) throws Exception {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            if (isVideoFile(Objects.requireNonNull(file.getOriginalFilename()))) {
                String fileUrl = uploadFile(fileName, file);
                return new FileDto(fileName, fileUrl);
            }
            throw new IllegalArgumentException("Uploaded file is not a video.");
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }

    @Override
    public boolean deleteFile(String fileName) throws Exception {
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            return storage.delete(blobId);
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        return originalFileName + new Date().getTime();
    }

    private String uploadFile(String fileName, MultipartFile file) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] fileData = file.getBytes();
        Blob blob = storage.create(blobInfo, fileData);

        return blob.getMediaLink();
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
        return fileName.toLowerCase().endsWith(".mp4") || fileName.toLowerCase().endsWith(".mov")
            || fileName.toLowerCase().endsWith(".avi") || fileName.toLowerCase().endsWith(".vmw");
    }
}
