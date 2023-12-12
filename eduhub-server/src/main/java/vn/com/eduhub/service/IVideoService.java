package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.VideoDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Video;

public interface IVideoService extends CommonService<Video, VideoDto> {
    ObjectDataRes<VideoDto> search(CommonSearchReq req);
}
