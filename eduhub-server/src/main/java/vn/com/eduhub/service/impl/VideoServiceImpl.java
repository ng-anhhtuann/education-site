package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.VideoDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Video;
import vn.com.eduhub.service.IVideoService;

public class VideoServiceImpl implements IVideoService{

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;
    
    @Override
    public Video edit(VideoDto d) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectDataRes<Video> getList(CommonSearchReq req) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VideoDto detail(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String delete(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
