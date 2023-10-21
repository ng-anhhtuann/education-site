package vn.com.eduhub.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.com.eduhub.dto.res.BaseRes;

public interface ErrorHandler {

    BaseRes handlerException(Exception ex, HttpServletRequest request, HttpServletResponse httpServletResponse,
                             long start);
}
