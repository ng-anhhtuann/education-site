package vn.com.eduhub.controller.handler.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.handler.SuccessHandler;
import vn.com.eduhub.dto.res.BaseRes;

import java.util.Date;

@Service
public class SuccessHandlerImpl implements SuccessHandler {

    @Override
    public BaseRes handlerSuccess(Object resObj, long start) {
        long took = System.currentTimeMillis() - start;
        return new BaseRes(Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "Successfully!", new Date(), took, null, resObj);
    }
}
