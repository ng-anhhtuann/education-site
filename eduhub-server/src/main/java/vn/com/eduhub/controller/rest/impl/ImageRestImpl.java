package vn.com.eduhub.controller.rest.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.controller.req.ImageAddReq;
import vn.com.eduhub.controller.rest.AbstractRest;
import vn.com.eduhub.controller.rest.IImageRest;
import vn.com.eduhub.dto.master.ImageDto;
import vn.com.eduhub.dto.res.BaseRes;
import vn.com.eduhub.service.IImageService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.IMAGE)
@Component
@Tag(name = ApiConstant.SWAGGER_IMAGE)
public class ImageRestImpl extends AbstractRest implements IImageRest {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    IImageService imageService;

    @Override
    public BaseRes add(ImageAddReq request, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            ImageDto dto = mapper.map(request, ImageDto.class);
            return this.successHandler.handlerSuccess(this.imageService.edit(dto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes list(CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.imageService.search(searchDto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, null, start);
        }
    }

    @Override
    public BaseRes detail(String id, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.imageService.detail(id), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes delete(String id, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.imageService.delete(id), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }
}
