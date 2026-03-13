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
import vn.com.eduhub.controller.req.CourseAddReq;
import vn.com.eduhub.controller.validation.CourseValidator;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.res.ApiResponse;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.service.ICourseService;

@RestController
@RequestMapping(UrlConst.COURSE)
@Tag(name = ApiConstant.SWAGGER_COURSE)
@RequiredArgsConstructor
public class CourseRestImpl {

    private final ICourseService courseService;
    private final CourseValidator validator;
    private final ModelMapper mapper;

    @PostMapping(UrlConst.EDIT)
    @Operation(summary = ApiConstant.UPDATE_OR_CREATE)
    public ResponseEntity<ApiResponse<CourseDto>> add(@Valid @RequestBody CourseAddReq request) {
        validator.validateEdit(request);
        CourseDto dto = mapper.map(request, CourseDto.class);
        return ResponseEntity.ok(ApiResponse.ok(courseService.edit(dto)));
    }

    @PostMapping(UrlConst.LIST)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    public ResponseEntity<ApiResponse<PagedResponse<CourseDto>>> list(@Valid @RequestBody CommonSearchReq searchDto) {
        return ResponseEntity.ok(ApiResponse.ok(courseService.search(searchDto)));
    }

    @GetMapping(UrlConst.DETAIL + "/{id}")
    @Operation(summary = ApiConstant.GET_DETAIL)
    public ResponseEntity<ApiResponse<CourseDto>> detail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(courseService.detail(id)));
    }

    @DeleteMapping(UrlConst.DELETE + "/{id}")
    @Operation(summary = ApiConstant.DELETE)
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(courseService.delete(id)));
    }
}
