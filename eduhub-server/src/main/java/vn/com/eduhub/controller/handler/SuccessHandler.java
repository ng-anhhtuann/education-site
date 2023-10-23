package vn.com.eduhub.controller.handler;

import vn.com.eduhub.dto.res.BaseRes;

public interface SuccessHandler {

    BaseRes handlerSuccess(Object resObj, long start);
}
