package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.master.SubscriptionDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.entity.Subscription;
import vn.com.eduhub.entity.User;

public interface ISubscriptionService extends CommonService<Subscription, SubscriptionDto> {
    ObjectDataRes<CourseDto> searchCourseByUser(CommonSearchReq req);

    ObjectDataRes<User> searchStudentByCourse(CommonSearchReq req);

    boolean checkSubscription(String userId, String courseId);

    SubscriptionDto createSubscription(SubscriptionDto dto) throws Exception;
}
