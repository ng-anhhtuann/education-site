package vn.com.eduhub.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.dto.res.BaseRes;

public interface FileRest {

    @PostMapping(value = UrlConst.UPLOAD_IMAGE)
    @Operation(summary = ApiConstant.IMAGE_UPLOAD)
    BaseRes uploadImage(@RequestParam("image") MultipartFile image, HttpServletRequest req, HttpServletResponse res);

    @PostMapping(value = UrlConst.UPLOAD_VIDEO)
    @Operation(summary = ApiConstant.VIDEO_UPLOAD)
    BaseRes uploadVideo(@RequestParam("video") MultipartFile video, HttpServletRequest req, HttpServletResponse res);

    @DeleteMapping(value = UrlConst.DELETE_FILE)
    @Operation(summary = ApiConstant.DELETE_FILE)
    BaseRes deleteFile(@RequestParam("fileName") String fileName, HttpServletRequest req, HttpServletResponse res);

}
