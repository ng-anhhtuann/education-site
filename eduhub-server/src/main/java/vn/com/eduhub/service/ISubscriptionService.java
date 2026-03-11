package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.master.SubscriptionDto;
import vn.com.eduhub.dto.master.UserDto;
import vn.com.eduhub.dto.res.PagedResponse;

public interface ISubscriptionService {

    SubscriptionDto createSubscription(SubscriptionDto dto);

    PagedResponse<CourseDto> searchCourseByUser(CommonSearchReq req);

    PagedResponse<UserDto> searchStudentByCourse(CommonSearchReq req);

    boolean checkSubscription(String userId, String courseId);
}
