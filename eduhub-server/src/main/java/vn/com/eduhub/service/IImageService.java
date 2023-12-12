package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.ImageDto;
import vn.com.eduhub.dto.master.VideoDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Image;

public interface IImageService extends CommonService<Image, ImageDto> {
    ObjectDataRes<ImageDto> search(CommonSearchReq req);
}
