package vn.com.eduhub.service;

import org.springframework.web.multipart.MultipartFile;
import vn.com.eduhub.dto.master.FileDto;

public interface IFileService {

    FileDto uploadImage(MultipartFile file) throws Exception;

    FileDto uploadVideo(MultipartFile file) throws Exception;

    boolean deleteFile(String fileName) throws Exception;

}
