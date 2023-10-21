package vn.com.eduhub.controller.rest.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.com.eduhub.constant.ApiConstant;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.controller.req.SubscriptionReq;
import vn.com.eduhub.controller.rest.AbstractRest;
import vn.com.eduhub.controller.rest.ISubscriptionRest;
import vn.com.eduhub.dto.master.SubscriptionDto;
import vn.com.eduhub.dto.res.BaseRes;
import vn.com.eduhub.service.ISubscriptionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(UrlConst.SUBSCRIPTION)
@Component
@Tag(name = ApiConstant.SWAGGER_SUBSCRIPTION)
public class SubscriptionRestImpl extends AbstractRest implements ISubscriptionRest{

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    ISubscriptionService subscriptionService;
	
	@Override
	public BaseRes add(SubscriptionReq request, HttpServletRequest req, HttpServletResponse res) {
		long start = System.currentTimeMillis();
        try {
            SubscriptionDto dto = mapper.map(request, SubscriptionDto.class);
            return this.successHandler.handlerSuccess(this.subscriptionService.edit(dto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, res, start);
        }
	}

	@Override
	public BaseRes list(CommonSearchReq searchDto, HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(this.subscriptionService.getList(searchDto), start);
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.errorHandler.handlerException(ex, req, null, start);
        }
	}

	@Override
    @Operation(hidden = true)
	public BaseRes detail(String id, HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    @Operation(hidden = true)
	public BaseRes delete(String id, HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

}
