package vn.com.eduhub.controller.rest;

import org.springframework.web.bind.annotation.*;
import vn.com.eduhub.constant.UrlConst;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.res.BaseRes;

public interface CommonRest<REQ> {

    @PostMapping(UrlConst.EDIT)
    BaseRes add(@RequestBody REQ req);

    @PostMapping(UrlConst.LIST)
    BaseRes list(@RequestBody CommonSearchReq searchDto);

    @GetMapping(UrlConst.DETAIL + UrlConst.SLASH + "{id}")
    BaseRes detail(@PathVariable("id") String id);

    @DeleteMapping(UrlConst.DELETE + UrlConst.SLASH + "{id}")
    BaseRes delete(@PathVariable("id") String id);

}
