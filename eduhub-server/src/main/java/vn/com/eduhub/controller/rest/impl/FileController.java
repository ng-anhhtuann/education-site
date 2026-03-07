package vn.com.eduhub.controller.rest.impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.Notification;
import vn.com.eduhub.controller.rest.AbstractRest;
import vn.com.eduhub.controller.rest.FileRest;
import vn.com.eduhub.dto.res.BaseRes;
import vn.com.eduhub.service.IFileService;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.FILE)
@Component
@Tag(name = ApiConstant.SWAGGER_FILE)
public class FileController extends AbstractRest implements FileRest {

    private String databaseUrl = "https://apartify-2fd8c-default-rtdb.asia-southeast1.firebasedatabase.app/";

    @Autowired
    IFileService fileService;

    @Override
    public BaseRes uploadImage(MultipartFile image, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.fileService.uploadImage(image), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes uploadVideo(MultipartFile video, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.fileService.uploadVideo(video), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes deleteFile(String fileName, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.fileService.deleteFile(fileName), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes notification(Notification noti, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance(databaseUrl);
            UUID id = UUID.randomUUID();
            String idStr = id.toString();
            DatabaseReference ref = database.getReference("newsFeed").child(idStr);
            ref.setValueAsync(noti);

            return this.successHandler.handlerSuccess("Data inserted successfully", start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }
}
