package vn.com.eduhub.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.UserAddReq;
import vn.com.eduhub.dto.auth.LogInDto;
import vn.com.eduhub.dto.res.BaseRes;

public interface IUserRest extends CommonRest<UserAddReq> {

    @PostMapping(value = UrlConst.LOGIN)
    @Operation(summary = ApiConstant.LOGIN)
    BaseRes login(@RequestBody LogInDto dto, HttpServletRequest req, HttpServletResponse res);

}
