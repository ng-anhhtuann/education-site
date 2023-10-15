package vn.com.eduhub.controller.rest.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.controller.req.CourseAddReq;
import vn.com.eduhub.controller.rest.ICourseRest;
import vn.com.eduhub.dto.res.BaseRes;
import vn.com.eduhub.service.ICourseService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.COURSE)
@Component
@Tag(name = ApiConstant.SWAGGER_COURSE)
public class CourseRestImpl implements ICourseRest {

    @Autowired
    ICourseService courseService;

    @Override
    public BaseRes add(CourseAddReq courseAddReq, HttpServletRequest req, HttpServletResponse res) {
        return new BaseRes();
    }

    @Override
    public BaseRes list(CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res) {
        return new BaseRes();
    }

    @Override
    public BaseRes detail(String id, HttpServletRequest req, HttpServletResponse res) {
        return new BaseRes();
    }

    @Override
    public BaseRes delete(String id, HttpServletRequest req, HttpServletResponse res) {
        return new BaseRes();
    }
}
