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
import vn.com.eduhub.controller.req.VideoAddReq;
import vn.com.eduhub.dto.master.VideoDto;
import vn.com.eduhub.dto.res.ApiResponse;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.service.IVideoService;

@RestController
@RequestMapping(UrlConst.VIDEO)
@Tag(name = ApiConstant.SWAGGER_VIDEO)
@RequiredArgsConstructor
public class VideoRestImpl {

    private final IVideoService videoService;
    private final ModelMapper mapper;

    @PostMapping(UrlConst.EDIT)
    @Operation(summary = ApiConstant.UPDATE_OR_CREATE)
    public ResponseEntity<ApiResponse<VideoDto>> add(@Valid @RequestBody VideoAddReq request) {
        VideoDto dto = mapper.map(request, VideoDto.class);
        return ResponseEntity.ok(ApiResponse.ok(videoService.edit(dto)));
    }

    @PostMapping(UrlConst.LIST)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    public ResponseEntity<ApiResponse<PagedResponse<VideoDto>>> list(@Valid @RequestBody CommonSearchReq searchDto) {
        return ResponseEntity.ok(ApiResponse.ok(videoService.search(searchDto)));
    }

    @GetMapping(UrlConst.DETAIL + "/{id}")
    @Operation(summary = ApiConstant.GET_DETAIL)
    public ResponseEntity<ApiResponse<VideoDto>> detail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(videoService.detail(id)));
    }

    @DeleteMapping(UrlConst.DELETE + "/{id}")
    @Operation(summary = ApiConstant.DELETE)
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(videoService.delete(id)));
    }
}
