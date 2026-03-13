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
import vn.com.eduhub.controller.req.ImageAddReq;
import vn.com.eduhub.dto.master.ImageDto;
import vn.com.eduhub.dto.res.ApiResponse;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.service.IImageService;

@RestController
@RequestMapping(UrlConst.IMAGE)
@Tag(name = ApiConstant.SWAGGER_IMAGE)
@RequiredArgsConstructor
public class ImageRestImpl {

    private final IImageService imageService;
    private final ModelMapper mapper;

    @PostMapping(UrlConst.EDIT)
    @Operation(summary = ApiConstant.UPDATE_OR_CREATE)
    public ResponseEntity<ApiResponse<ImageDto>> add(@Valid @RequestBody ImageAddReq request) {
        ImageDto dto = mapper.map(request, ImageDto.class);
        return ResponseEntity.ok(ApiResponse.ok(imageService.edit(dto)));
    }

    @PostMapping(UrlConst.LIST)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    public ResponseEntity<ApiResponse<PagedResponse<ImageDto>>> list(@Valid @RequestBody CommonSearchReq searchDto) {
        return ResponseEntity.ok(ApiResponse.ok(imageService.search(searchDto)));
    }

    @GetMapping(UrlConst.DETAIL + "/{id}")
    @Operation(summary = ApiConstant.GET_DETAIL)
    public ResponseEntity<ApiResponse<ImageDto>> detail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(imageService.detail(id)));
    }

    @DeleteMapping(UrlConst.DELETE + "/{id}")
    @Operation(summary = ApiConstant.DELETE)
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(imageService.delete(id)));
    }
}
