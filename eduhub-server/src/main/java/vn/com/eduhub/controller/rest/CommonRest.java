package vn.com.eduhub.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.res.BaseRes;

public interface CommonRest<REQ> {

    @PostMapping(value = UrlConst.EDIT)
    @Operation(summary = ApiConstant.UPDATE_OR_CREATE)
    BaseRes add(@RequestBody REQ request, HttpServletRequest req, HttpServletResponse res);

    @PostMapping(value = UrlConst.LIST)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    BaseRes list(@RequestBody CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res);

    @GetMapping(value = UrlConst.DETAIL + UrlConst.SLASH + "{id}")
    @Operation(summary = ApiConstant.GET_DETAIL)
    BaseRes detail(@PathVariable("id") String id, HttpServletRequest req, HttpServletResponse res);

    @DeleteMapping(value = UrlConst.DELETE + UrlConst.SLASH + "{id}")
    @Operation(summary = ApiConstant.DELETE)
    BaseRes delete(@PathVariable("id") String id, HttpServletRequest req, HttpServletResponse res);

}
