package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.ImageDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Image;
import vn.com.eduhub.service.IImageService;


public class ImageServiceImpl implements IImageService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;
    
    @Override
    public Image edit(ImageDto d) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectDataRes<Image> getList(CommonSearchReq req) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ImageDto detail(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String delete(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
