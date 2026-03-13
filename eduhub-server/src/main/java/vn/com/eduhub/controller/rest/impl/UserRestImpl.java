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
import vn.com.eduhub.controller.req.UserAddReq;
import vn.com.eduhub.controller.validation.UserValidator;
import vn.com.eduhub.dto.auth.LogInDto;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.master.UserDto;
import vn.com.eduhub.dto.res.ApiResponse;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.service.IUserService;

@RestController
@RequestMapping(UrlConst.USER)
@Tag(name = ApiConstant.SWAGGER_USER)
@RequiredArgsConstructor
public class UserRestImpl {

    private final IUserService userService;
    private final UserValidator validator;
    private final ModelMapper mapper;

    @PostMapping(UrlConst.EDIT)
    @Operation(summary = ApiConstant.UPDATE_OR_CREATE)
    public ResponseEntity<ApiResponse<UserDto>> add(@Valid @RequestBody UserAddReq request) {
        validator.validateEdit(request);
        SignUpDto dto = mapper.map(request, SignUpDto.class);
        UserDto result;
        if (request.getId() == null || request.getId().isBlank()) {
            result = userService.register(dto);
        } else {
            dto.setId(request.getId());
            result = userService.update(dto);
        }
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PostMapping(UrlConst.LIST)
    @Operation(summary = ApiConstant.GET_SEARCH_LIST)
    public ResponseEntity<ApiResponse<PagedResponse<UserDto>>> list(@Valid @RequestBody CommonSearchReq searchDto) {
        return ResponseEntity.ok(ApiResponse.ok(userService.search(searchDto)));
    }

    @GetMapping(UrlConst.DETAIL + "/{id}")
    @Operation(summary = ApiConstant.GET_DETAIL)
    public ResponseEntity<ApiResponse<UserDto>> detail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.detail(id)));
    }

    @PostMapping(UrlConst.LOGIN)
    @Operation(summary = ApiConstant.LOGIN)
    public ResponseEntity<ApiResponse<UserDto>> login(@Valid @RequestBody LogInDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(userService.login(dto)));
    }
}
