package vn.com.eduhub.controller.rest.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.controller.req.UserAddReq;
import vn.com.eduhub.controller.rest.IUserRest;
import vn.com.eduhub.dto.res.BaseRes;
import vn.com.eduhub.service.IUserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.USER)
@Component
@Tag(name = ApiConstant.SWAGGER_USER)
public class UserRestImpl implements IUserRest {

    @Autowired
    IUserService userService;

    @Override
    public BaseRes add(UserAddReq userAddReq) {
        return new BaseRes();
    }

    @Override
    public BaseRes list(CommonSearchReq searchDto) {
        return new BaseRes();
    }

    @Override
    public BaseRes detail(String id) {
        return new BaseRes();
    }

    @Override
    public BaseRes delete(String id) {
        return new BaseRes();
    }
}
