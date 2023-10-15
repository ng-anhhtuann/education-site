package vn.com.eduhub.controller.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.eduhub.controller.handler.ErrorHandler;
import vn.com.eduhub.controller.handler.SuccessHandler;

@Getter
@Setter
public abstract class AbstractRest {

    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected SuccessHandler successHandler;
}
