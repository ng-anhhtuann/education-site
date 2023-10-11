package vn.com.eduhub.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.res.BaseRes;

public interface CommonRest<REQ> {

    @PostMapping(value = UrlConst.EDIT)
    @Operation(summary = ApiConstant.UPDATE_OR_CREATE)
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = ApiConstant.SUCCESS_CODE, description = ApiConstant.SUCCESS),
//        @ApiResponse(responseCode = ApiConstant.INTERNAL_SERVER_ERROR_CODE, description = ApiConstant.INTERNAL_SERVER_ERROR),
//        @ApiResponse(responseCode = ApiConstant.UNAUTHORIZED_CODE, description = ApiConstant.UNAUTHORIZED),
//        @ApiResponse(responseCode = ApiConstant.FORBIDDEN_CODE, description = ApiConstant.FORBIDDEN)})
    BaseRes add(@RequestBody REQ req);

    @PostMapping(value = UrlConst.LIST)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = ApiConstant.SUCCESS_CODE, description = ApiConstant.SUCCESS),
//        @ApiResponse(responseCode = ApiConstant.INTERNAL_SERVER_ERROR_CODE, description = ApiConstant.INTERNAL_SERVER_ERROR),
//        @ApiResponse(responseCode = ApiConstant.UNAUTHORIZED_CODE, description = ApiConstant.UNAUTHORIZED),
//        @ApiResponse(responseCode = ApiConstant.FORBIDDEN_CODE, description = ApiConstant.FORBIDDEN)})
    BaseRes list(@RequestBody CommonSearchReq searchDto);

    @GetMapping(value = UrlConst.DETAIL + UrlConst.SLASH + "{id}")
    @Operation(summary = ApiConstant.GET_DETAIL)
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = ApiConstant.SUCCESS_CODE, description = ApiConstant.SUCCESS),
//        @ApiResponse(responseCode = ApiConstant.INTERNAL_SERVER_ERROR_CODE, description = ApiConstant.INTERNAL_SERVER_ERROR),
//        @ApiResponse(responseCode = ApiConstant.UNAUTHORIZED_CODE, description = ApiConstant.UNAUTHORIZED),
//        @ApiResponse(responseCode = ApiConstant.FORBIDDEN_CODE, description = ApiConstant.FORBIDDEN)})
    BaseRes detail(@PathVariable("id") String id);

    @DeleteMapping(value = UrlConst.DELETE + UrlConst.SLASH + "{id}")
    @Operation(summary = ApiConstant.DELETE)
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = ApiConstant.SUCCESS_CODE, description = ApiConstant.SUCCESS),
//        @ApiResponse(responseCode = ApiConstant.INTERNAL_SERVER_ERROR_CODE, description = ApiConstant.INTERNAL_SERVER_ERROR),
//        @ApiResponse(responseCode = ApiConstant.UNAUTHORIZED_CODE, description = ApiConstant.UNAUTHORIZED),
//        @ApiResponse(responseCode = ApiConstant.FORBIDDEN_CODE, description = ApiConstant.FORBIDDEN)})
    BaseRes delete(@PathVariable("id") String id);

}
