package vn.com.eduhub.controller.rest.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.controller.req.UserAddReq;
import vn.com.eduhub.controller.rest.AbstractRest;
import vn.com.eduhub.controller.rest.IUserRest;
import vn.com.eduhub.controller.validation.UserValidator;
import vn.com.eduhub.dto.auth.LogInDto;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.res.BaseRes;
import vn.com.eduhub.service.IUserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.USER)
@Component
@Tag(name = ApiConstant.SWAGGER_USER)
public class UserRestImpl extends AbstractRest implements IUserRest {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    IUserService userService;

    @Autowired
    UserValidator validator;

    @Override
    public BaseRes add(UserAddReq request, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            validator.validateEdit(request);
            SignUpDto dto = mapper.map(request, SignUpDto.class);
            return this.successHandler.handlerSuccess(this.userService.edit(dto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes list(CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.userService.getList(searchDto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, null, start);
        }
    }

    @Override
    public BaseRes detail(String id, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.userService.detail(id), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    @Operation(hidden = true)
    public BaseRes delete(String id, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

    @Override
    public BaseRes login(LogInDto dto, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.userService.login(dto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
    }

}
