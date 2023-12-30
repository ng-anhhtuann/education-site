package vn.com.eduhub.service.impl;

import lombok.extern.slf4j.Slf4j;
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
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements ICourseService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    IUserService userService;

    /**
     * Mô tả: id : id của khoá học cần cập nhật (nếu có id truyền vào khác null) price: giá tiền mua khoá học title : tên/tiêu đề course
     * tagList : thẻ từ khoá liên quan đến khoá học teacherId : id user truyền vào tương ứng người tạo khoá học description : mô tả khoá học
     * tổng quan thumbnailUrl : link dẫn đến hình ảnh thumbnail khoá học
     * <p>
     * Khi tạo mới course thì chỉ cho phép tạo mới theo các field: price, title, tagList, teacherId, description, thumbnailUrl
     * <p>
     * Khi cập nhật khoá học chỉ cho phép cập nhật theo các field: price, title, tagList, description, thumbnailUrl
     *
     * @throws Exception
     */
    @Override
    public Course edit(CourseDto dto) throws Exception {
        if (dto.getId() == null || dto.getId().isEmpty() || dto.getId().isBlank()) {
            Course course = mapper.map(dto, Course.class);
            course.setId(String.valueOf(UUID.randomUUID()).concat(String.valueOf(System.currentTimeMillis())));
            course.setStudentCount(0L);
            course.setUpdatedDate(null);
            course.setCreatedDate(new Date());
            courseRepository.insert(course);
            return course;
        } else {
            Optional<Course> courseOptional = courseRepository.findById(dto.getId());
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                course.setUpdatedDate(new Date());
                if (dto.getPrice() != null)
                    course.setPrice(dto.getPrice());
                if (dto.getTitle() != null || !dto.getTitle().trim().isEmpty()) {
                    course.setTitle(dto.getTitle());
                } else {
                    throw new Exception(CommonConstant.EMPTY_TITLE);
                }

                if (dto.getTagList() != null)
                    course.setTagList(dto.getTagList());
                if (dto.getDescription() != null || !dto.getDescription().trim().isEmpty()) {
                    course.setDescription(dto.getDescription());
                } else {
                    throw new Exception(CommonConstant.EMPTY_DESCRIPTION);
                }
                if (dto.getThumbnailUrl() != null || !dto.getThumbnailUrl().trim().isEmpty()) {
                    course.setThumbnailUrl(dto.getThumbnailUrl());
                } else {
                    throw new Exception(CommonConstant.PROCESS_FAIL);
                }
                courseRepository.save(course);
                return course;
            } else {
                throw new Exception(CommonConstant.COURSE_NOT_FOUND);
            }
        }
    }

    @Override
    public ObjectDataRes<Course> getList(CommonSearchReq req) {
        return null;
    }

    /**
     * @flow Search field được cho phép ở course là: + min_price là cận dưới của price + max_price là cận trên của price + title + tag_list
     * (mảng các tag topic liên quan đến khóa học) Datatype của các field search đều là String Dynamic search lúc này kiểm tra chuỗi
     * có chứa chuỗi con hay không. Nếu page = 0 thì là lấy hết record theo trạng thái search Nếu searchType = "ALL" thì sẽ là search
     * tất cả mặc kệ các params Nếu searchType = "FIELD" thì sẽ là search theo params
     * @default Sort by date created
     */
    @Override
    public ObjectDataRes<CourseDto> search(CommonSearchReq req) {
        List<Course> listData = new ArrayList<>();
        List<CourseDto> courseDtoList;
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
            listData = mongoTemplate.find(query, Course.class);
        }
        if (req.getSearchType().equals("FIELD") && req.getParams() != null) {
            Criteria criteria = new Criteria();
            List<Criteria> criteriaList = new ArrayList<>();

            Object minPrice = req.getParams().get("min_price");
            Object maxPrice = req.getParams().get("max_price");
            Object tagList = req.getParams().get("tag_list");
            Object title = req.getParams().get("title");
            String teacherId = (String) req.getParams().get("teacher_id");

            if (minPrice == null && maxPrice == null && tagList == null && title == null) {
                criteriaList.add(Criteria.where("teacher_id").is(teacherId));
            } else if (minPrice != null && maxPrice != null && title != null) {
                if (tagList instanceof ArrayList) {
                    criteriaList.add(Criteria.where("price").gte(minPrice).lte(maxPrice));
                    criteriaList.add(Criteria.where("tag_list").in(tagList));
                    criteriaList.add(Criteria.where("title").regex(String.valueOf(title), "i"));
                } else {
                    criteriaList.add(Criteria.where("price").gte(minPrice).lte(maxPrice));
                    criteriaList.add(Criteria.where("title").regex(String.valueOf(title), "i"));
                }
            }
            criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            query.addCriteria(criteria);
            listData = mongoTemplate.find(query, Course.class);
        }

        courseDtoList = listData.stream().map(course -> {
            CourseDto courseDto = mapper.map(course, CourseDto.class);
            try {
                courseDto.setTeacherName(userService.detail(course.getTeacherId()).getUserName());
            } catch (Exception e) {
                e.printStackTrace();
                courseDto.setTeacherName("UNKNOWN");
            }
            return courseDto;
        }).collect(Collectors.toList());

        query.skip(0);
        query.limit(0);
        int size = mongoTemplate.find(query, Course.class).size();

        return new ObjectDataRes<>(size, courseDtoList);
    }

    @Override
    public CourseDto detail(String id) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            throw new Exception(CommonConstant.COURSE_NOT_FOUND);
        }
        Course course = courseOptional.get();
        CourseDto courseDto = mapper.map(course, CourseDto.class);
        try {
            courseDto.setTeacherName(userService.detail(course.getTeacherId()).getUserName());
        } catch (Exception e) {
            e.printStackTrace();
            courseDto.setTeacherName("UNKNOWN");
        }
        return courseDto;
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
