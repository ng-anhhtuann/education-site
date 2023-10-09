package vn.com.eduhub.controller.rest.impl;

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

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.SLASH + ApiConstant.USER)
@Component
public class UserRestImpl implements IUserRest {

    @Override
    public BaseRes add(UserAddReq userAddReq) {
        return null;
    }

    @Override
    public BaseRes list(CommonSearchReq searchDto) {
        return null;
    }

    @Override
    public BaseRes detail(String id) {
        return null;
    }

    @Override
    public BaseRes delete(String id) {
        return null;
    }
}
