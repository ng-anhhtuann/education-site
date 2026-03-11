package vn.com.eduhub.service;

import org.springframework.web.multipart.MultipartFile;
import vn.com.eduhub.dto.master.FileDto;

public interface IFileService {

    FileDto uploadImage(MultipartFile file);

    FileDto uploadVideo(MultipartFile file);

    boolean deleteFile(String fileName);
}
