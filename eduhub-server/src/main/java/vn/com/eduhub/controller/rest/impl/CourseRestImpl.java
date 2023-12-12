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
import vn.com.eduhub.controller.req.CourseAddReq;
import vn.com.eduhub.controller.rest.AbstractRest;
import vn.com.eduhub.controller.rest.ICourseRest;
import vn.com.eduhub.controller.validation.CourseValidator;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.res.BaseRes;
import vn.com.eduhub.service.ICourseService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.COURSE)
@Component
@Tag(name = ApiConstant.SWAGGER_COURSE)
public class CourseRestImpl extends AbstractRest implements ICourseRest {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    CourseValidator validator;

    @Autowired
    ICourseService courseService;

    @Override
    public BaseRes add(CourseAddReq courseAddReq, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            validator.validateEdit(courseAddReq);
            CourseDto dto = mapper.map(courseAddReq, CourseDto.class);
            return this.successHandler.handlerSuccess(this.courseService.edit(dto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes list(CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.courseService.search(searchDto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, null, start);
        }
    }

    @Override
    public BaseRes detail(String id, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.courseService.detail(id), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes delete(String id, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.courseService.delete(id), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }
}
