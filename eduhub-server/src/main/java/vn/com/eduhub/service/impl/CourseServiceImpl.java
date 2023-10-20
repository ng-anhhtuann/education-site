package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.service.ICourseService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.*;

@Service
public class CourseServiceImpl implements ICourseService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CourseRepository courseRepository;

    /**
     * Mô tả:
     * id : id của khoá học cần cập nhật (nếu có id truyền vào khác null)
     * price: giá tiền mua khoá học
     * title : tên/tiêu đề course
     * tagList : thẻ từ khoá liên quan đến khoá học
     * teacherId : id user truyền vào tương ứng người tạo khoá học
     * description : mô tả khoá học tổng quan
     * thumbnailUrl : link dẫn đến hình ảnh thumbnail khoá học
     * <p>
     * Khi tạo mới course thì chỉ cho phép tạo mới theo các field:
     * price, title, tagList, teacherId, description, thumbnailUrl
     * <p>
     * Khi cập nhật khoá học chỉ cho phép cập nhật theo các field:
     * price, title, tagList, description, thumbnailUrl
     * @throws Exception 
     */
    @Override
    public Course edit(CourseDto dto) throws Exception {
        if (dto.getId() == null || dto.getId().isEmpty() || dto.getId().isBlank()) {
            Course course = mapper.map(dto, Course.class);
            course.setId(String.valueOf(UUID.randomUUID()).concat(String.valueOf(System.currentTimeMillis())));
            course.setStudentCount(0L);
            course.setCreatedDate(new Date());
            courseRepository.insert(course);
            return course;
        } else {
            Optional<Course> courseOptional = courseRepository.findById(dto.getId());
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                course.setUpdatedDate(new Date());
                if (dto.getPrice() != null ) course.setPrice(dto.getPrice());
                if (dto.getTitle() != null ) course.setTitle(dto.getTitle());
                if (dto.getTagList() != null ) course.setTagList(dto.getTagList());
                if (dto.getDescription() != null ) course.setDescription(dto.getDescription());
                if (dto.getThumbnailUrl() != null ) course.setThumbnailUrl(dto.getThumbnailUrl());
                courseRepository.save(course);
                return course;
            } else {
            	throw new Exception(CommonConstant.COURSE_NOT_FOUND);
            }
        }
    }

    /**
     * @flow Search field được cho phép ở course là:
     * + min_price là cận dưới của price
     * + max_price là cận trên của price
     * + title
     * + tag_list (mảng các tag topic liên quan đến khóa học)
     * Datatype của các field search đều là String
     * Dynamic search lúc này kiểm tra chuỗi có chứa chuỗi con hay không.
     * Nếu page = 0 thì là lấy hết record theo trạng thái search
     * Nếu searchType = "ALL" thì sẽ là search tất cả mặc kệ các params
     * Nếu searchType = "FIELD" thì sẽ là search theo params
     * @default Sort by date created
     */
    @Override
    public ObjectDataRes<Course> getList(CommonSearchReq req) {
        List<Course> listData = new ArrayList<>();
        
        Query query = new Query();
        
        query.with(Sort.by(Sort.Order.desc("created_date")));

        if (req.getPage() != null && req.getPage() > 0 && req.getPageSize() != null && req.getPageSize() >= 0) {
            query.skip((long) (req.getPage() - 1) * req.getPageSize());
            query.limit(req.getPageSize());
        }
        
		/**
		 * Limit the number of returned documents to limit. 
		 * A zero or negative value is considered as unlimited.
		 */
        if ((req.getPage() != null && req.getPage() == 0) || (req.getPageSize() == null && req.getPage() == null)) {
            query.limit(0);
        }

        if (req.getSearchType().equals("ALL")) {
            listData = mongoTemplate.find(query, Course.class);
        }
        if (req.getSearchType().equals("FIELD") && req.getParams() != null) {
            Criteria criteria = new Criteria();
            for (Map.Entry<String, Object> entry : req.getParams().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if ("min_price".equals(key) && value instanceof Long) {
                    criteria.andOperator(Criteria.where("price").gte(value));
                } else if ("max_price".equals(key) && value instanceof Long) {
                    criteria.andOperator(Criteria.where("price").lte(value));
                } else if ("tag_list".equals(key) && value instanceof String[]) {
                    criteria.andOperator(Criteria.where(key).in(value));
                } else if (value instanceof String) {
                    criteria.andOperator(Criteria.where(key).regex(String.valueOf(value), "i"));
                }
            }

            query.addCriteria(criteria);
            listData = mongoTemplate.find(query, Course.class);
        }

        return new ObjectDataRes<>(listData.size(), listData);
    }

    @Override
    public CourseDto detail(String id) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
        	throw new Exception(CommonConstant.COURSE_NOT_FOUND);
        }
        return mapper.map(courseOptional.get(), CourseDto.class);
    }

    @Override
    public String delete(String id) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
        	throw new Exception(CommonConstant.COURSE_NOT_FOUND);
        }
        try {
        	courseRepository.deleteById(id);
        	return id;
        } catch (Exception ex) {
        	throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }
}
