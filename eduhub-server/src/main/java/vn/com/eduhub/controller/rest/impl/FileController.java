package vn.com.eduhub.controller.rest.impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.Notification;
import vn.com.eduhub.dto.master.FileDto;
import vn.com.eduhub.dto.res.ApiResponse;
import vn.com.eduhub.service.IFileService;

import java.util.UUID;

@RestController
@RequestMapping(UrlConst.FILE)
@Tag(name = ApiConstant.SWAGGER_FILE)
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;

    @Value("${firebase.database-url}")
    private String databaseUrl;

    @PostMapping(UrlConst.UPLOAD_IMAGE)
    @Operation(summary = ApiConstant.IMAGE_UPLOAD)
    public ResponseEntity<ApiResponse<FileDto>> uploadImage(@RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(ApiResponse.ok(fileService.uploadImage(image)));
    }

    @PostMapping(UrlConst.UPLOAD_VIDEO)
    @Operation(summary = ApiConstant.VIDEO_UPLOAD)
    public ResponseEntity<ApiResponse<FileDto>> uploadVideo(@RequestParam("video") MultipartFile video) {
        return ResponseEntity.ok(ApiResponse.ok(fileService.uploadVideo(video)));
    }

    @DeleteMapping(UrlConst.DELETE_FILE)
    @Operation(summary = ApiConstant.DELETE_FILE)
    public ResponseEntity<ApiResponse<Boolean>> deleteFile(@RequestParam("fileName") String fileName) {
        return ResponseEntity.ok(ApiResponse.ok(fileService.deleteFile(fileName)));
    }

    @PostMapping("/notification")
    @Operation(summary = "Push notification to Firebase Realtime DB")
    public ResponseEntity<ApiResponse<String>> notification(@RequestBody Notification noti) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseUrl);
        DatabaseReference ref = database.getReference("newsFeed").child(UUID.randomUUID().toString());
        ref.setValueAsync(noti);
        return ResponseEntity.ok(ApiResponse.ok("Data inserted successfully"));
    }
}
