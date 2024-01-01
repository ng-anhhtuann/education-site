package vn.com.eduhub.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.controller.req.SubscriptionReq;
import vn.com.eduhub.dto.res.BaseRes;

public interface ISubscriptionRest extends CommonRest<SubscriptionReq> {

    @PostMapping(value = UrlConst.LIST + UrlConst.COURSE)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    BaseRes listCourse(@RequestBody CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res);

    @PostMapping(value = UrlConst.LIST + UrlConst.USER)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    BaseRes listUser(@RequestBody CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res);

}
