package vn.com.eduhub.controller.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.handler.ErrorHandler;
import vn.com.eduhub.dto.res.BaseRes;

import java.util.Date;

@Service
public class ErrorHandlerImpl implements ErrorHandler {

    @Override
    public BaseRes handlerException(Exception ex, HttpServletRequest req, HttpServletResponse res, long start) {
        long took = start != 0 ? (System.currentTimeMillis() - start) : start;
        return new BaseRes((long) HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Something's wrong!", new Date(), took,
                ex.getMessage(), null);
    }
}
