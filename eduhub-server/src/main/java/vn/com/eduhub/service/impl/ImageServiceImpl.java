package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.ImageDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.entity.Image;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.entity.Video;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.ImageRepository;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.ICourseService;
import vn.com.eduhub.service.IImageService;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements IImageService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ICourseService courseService;

    @Autowired
    IUserService userService;

    /**
     * Thêm mới hoặc chỉnh sửa Chỉnh sửa chỉ theo các field name và url
     */
    @Override
    public Image edit(ImageDto dto) throws Exception {
        if (dto.getId() == null || dto.getId().isEmpty() || dto.getId().isBlank()) {
            Image img = mapper.map(dto, Image.class);
            if (img.getIsAvatar()) {
                Optional<User> userOptional = userRepository.findById(img.getOwnerId());
                if (userOptional.isEmpty())
                    throw new Exception(CommonConstant.USER_NOT_FOUND);
            } else {
                Optional<Course> courseOptional = courseRepository.findById(img.getOwnerId());
                if (courseOptional.isEmpty())
                    throw new Exception(CommonConstant.COURSE_NOT_FOUND);
            }
            img.setId(String.valueOf(UUID.randomUUID()).concat(String.valueOf(System.currentTimeMillis())));
            img.setCreatedDate(null);
            img.setCreatedDate(new Date());
            imageRepository.insert(img);
            return img;
        } else {
            Optional<Image> imageOptional = imageRepository.findById(dto.getId());
            if (imageOptional.isPresent()) {
                Image img = imageOptional.get();
                img.setUpdatedDate(new Date());
                if (dto.getName() != null)
                    img.setName(dto.getName());
                if (dto.getUrl() != null)
                    img.setUrl(dto.getUrl());
                imageRepository.save(img);
                return img;
            } else {
                throw new Exception(CommonConstant.FILE_NOT_FOUND);
            }
        }
    }

    /**
     * Tìm kiếm danh sách theo is_avatar và owner_id
     */
    @Override
    public ObjectDataRes<Image> getList(CommonSearchReq req) {
        return null;
    }

    @Override
    public ImageDto detail(String id) throws Exception {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) {
            throw new Exception(CommonConstant.FILE_NOT_FOUND);
        }
        Image image = imageOptional.get();
        ImageDto imageDto = mapper.map(image, ImageDto.class);
        try {
            imageDto.setOwnerName(image.getIsAvatar() ? userService.detail(image.getOwnerId()).getUserName()
                    : courseService.detail(image.getOwnerId()).getTeacherName());
        } catch (Exception e) {
            e.printStackTrace();
            imageDto.setOwnerName("UNKNOWN");
        }
        return imageDto;
    }

    @Override
    public String delete(String id) throws Exception {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) {
            throw new Exception(CommonConstant.FILE_NOT_FOUND);
        }
        try {
            imageRepository.deleteById(id);
            return id;
        } catch (Exception ex) {
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }

    @Override
    public ObjectDataRes<ImageDto> search(CommonSearchReq req) {
        List<Image> listData = new ArrayList<>();
        List<ImageDto> dtoList;

        Query query = new Query();

        query.with(Sort.by(Sort.Order.desc("created_date")));

        if (req.getPage() != null && req.getPage() > 0 && req.getPageSize() != null && req.getPageSize() >= 0) {
            query.skip((long) (req.getPage() - 1) * req.getPageSize());
            query.limit(req.getPageSize());
        }

        /**
         * Limit the number of returned documents to limit. A zero or negative value is considered as unlimited.
         */
        if ((req.getPage() != null && req.getPage() == 0) || (req.getPageSize() == null && req.getPage() == null)) {
            query.limit(0);
        }

        if (req.getSearchType().equals("ALL")) {
            listData = mongoTemplate.find(query, Image.class);
        }
        if (req.getSearchType().equals("FIELD") && req.getParams() != null) {
            Criteria criteria = new Criteria();
            List<Criteria> criteriaList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : req.getParams().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                criteriaList.add(Criteria.where(key).is(value));
            }

            if (!criteriaList.isEmpty()) {
                criteria.andOperator(criteriaList.toArray(new Criteria[0]));
                query.addCriteria(criteria);
                listData = mongoTemplate.find(query, Image.class);
            }
        }

        dtoList = listData.stream().map(image -> {
            ImageDto imageDto = mapper.map(image, ImageDto.class);
            try {
                imageDto.setOwnerName(image.getIsAvatar() ? userService.detail(image.getOwnerId()).getUserName()
                        : courseService.detail(image.getOwnerId()).getTeacherName());
            } catch (Exception e) {
                e.printStackTrace();
                imageDto.setOwnerName("UNKNOWN");
            }
            return imageDto;
        }).collect(Collectors.toList());

        query.skip(0);
        query.limit(0);
        int size = mongoTemplate.find(query, Image.class).size();

        return new ObjectDataRes<>(dtoList.size(), dtoList);
    }
}
