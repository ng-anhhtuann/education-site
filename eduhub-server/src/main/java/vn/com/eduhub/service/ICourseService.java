package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Course;

public interface ICourseService extends CommonService<Course, CourseDto> {
    ObjectDataRes<CourseDto> search(CommonSearchReq req);
}
