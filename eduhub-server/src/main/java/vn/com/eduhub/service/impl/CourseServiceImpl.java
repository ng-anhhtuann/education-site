package vn.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.service.ICourseService;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course edit(CourseDto d) {
        return null;
    }

    @Override
    public ObjectDataRes<Course> getList(CommonSearchReq req) {
        return null;
    }

    @Override
    public Course detail(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
