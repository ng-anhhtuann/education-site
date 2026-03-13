package vn.com.eduhub.controller.rest.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.controller.req.SubscriptionReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.master.SubscriptionDto;
import vn.com.eduhub.dto.master.UserDto;
import vn.com.eduhub.dto.res.ApiResponse;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.service.ISubscriptionService;

@RestController
@RequestMapping(UrlConst.SUBSCRIPTION)
@Tag(name = ApiConstant.SWAGGER_SUBSCRIPTION)
@RequiredArgsConstructor
public class SubscriptionRestImpl {

    private final ISubscriptionService subscriptionService;
    private final ModelMapper mapper;

    @PostMapping(UrlConst.EDIT)
    @Operation(summary = ApiConstant.SUBSCRIPTION)
    public ResponseEntity<ApiResponse<SubscriptionDto>> add(@Valid @RequestBody SubscriptionReq request) {
        SubscriptionDto dto = mapper.map(request, SubscriptionDto.class);
        return ResponseEntity.ok(ApiResponse.ok(subscriptionService.createSubscription(dto)));
    }

    @PostMapping(UrlConst.LIST + UrlConst.COURSE)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    public ResponseEntity<ApiResponse<PagedResponse<CourseDto>>> listCourse(@Valid @RequestBody CommonSearchReq searchDto) {
        return ResponseEntity.ok(ApiResponse.ok(subscriptionService.searchCourseByUser(searchDto)));
    }

    @PostMapping(UrlConst.LIST + UrlConst.USER)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    public ResponseEntity<ApiResponse<PagedResponse<UserDto>>> listUser(@Valid @RequestBody CommonSearchReq searchDto) {
        return ResponseEntity.ok(ApiResponse.ok(subscriptionService.searchStudentByCourse(searchDto)));
    }

    @GetMapping(UrlConst.CHECK + "/{userId}/{courseId}")
    @Operation(summary = ApiConstant.CHECK_SUB)
    public ResponseEntity<ApiResponse<Boolean>> checkSubscription(@PathVariable String userId, @PathVariable String courseId) {
        return ResponseEntity.ok(ApiResponse.ok(subscriptionService.checkSubscription(userId, courseId)));
    }
}
