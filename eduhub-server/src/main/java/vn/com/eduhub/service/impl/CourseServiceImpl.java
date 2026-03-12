package vn.com.eduhub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.exception.ResourceNotFoundException;
import vn.com.eduhub.exception.ValidationException;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.service.ICourseService;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.service.SearchHelper;
import vn.com.eduhub.utils.CommonConstant;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final ModelMapper mapper;
    private final CourseRepository courseRepository;
    private final IUserService userService;
    private final SearchHelper searchHelper;

    @Override
    public CourseDto edit(CourseDto dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            return createCourse(dto);
        }
        return updateCourse(dto);
    }

    private CourseDto createCourse(CourseDto dto) {
        Course course = mapper.map(dto, Course.class);
        course.setId(UUID.randomUUID().toString() + System.currentTimeMillis());
        course.setStudentCount(0L);
        course.setCreatedDate(Instant.now());
        courseRepository.insert(course);
        log.info("Course created: {}", course.getTitle());
        return enrichWithTeacher(mapper.map(course, CourseDto.class), course.getTeacherId());
    }

    private CourseDto updateCourse(CourseDto dto) {
        Course course = courseRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.COURSE_NOT_FOUND));

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new ValidationException(CommonConstant.EMPTY_TITLE);
        }
        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new ValidationException(CommonConstant.EMPTY_DESCRIPTION);
        }

        course.setUpdatedDate(Instant.now());
        if (dto.getPrice() != null) course.setPrice(dto.getPrice());
        course.setTitle(dto.getTitle());
        if (dto.getTagList() != null) course.setTagList(dto.getTagList());
        course.setDescription(dto.getDescription());
        if (dto.getThumbnailUrl() != null) course.setThumbnailUrl(dto.getThumbnailUrl());

        courseRepository.save(course);
        log.info("Course updated: {}", course.getId());
        return enrichWithTeacher(mapper.map(course, CourseDto.class), course.getTeacherId());
    }

    @Override
    public PagedResponse<CourseDto> search(CommonSearchReq req) {
        SearchHelper.SearchResult<Course> result = searchHelper.search(req, Course.class, (params, criteriaList) -> {
            Object minPrice = params.get("min_price");
            Object maxPrice = params.get("max_price");
            Object tagList = params.get("tag_list");
            Object title = params.get("title");
            String teacherId = (String) params.get("teacher_id");

            if (minPrice == null && maxPrice == null && tagList == null && title == null && teacherId != null) {
                criteriaList.add(Criteria.where("teacher_id").is(teacherId));
            } else {
                if (minPrice != null && maxPrice != null) {
                    criteriaList.add(Criteria.where("price").gte(minPrice).lte(maxPrice));
                }
                if (tagList instanceof ArrayList) {
                    criteriaList.add(Criteria.where("tag_list").all(tagList));
                }
                if (title != null) {
                    criteriaList.add(Criteria.where("title").regex(String.valueOf(title), "i"));
                }
            }
        });

        var dtos = result.items().stream()
                .map(c -> enrichWithTeacher(mapper.map(c, CourseDto.class), c.getTeacherId()))
                .collect(Collectors.toList());

        return new PagedResponse<>(result.total(), dtos);
    }

    @Override
    public CourseDto detail(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.COURSE_NOT_FOUND));
        return enrichWithTeacher(mapper.map(course, CourseDto.class), course.getTeacherId());
    }

    @Override
    public String delete(String id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException(CommonConstant.COURSE_NOT_FOUND);
        }
        courseRepository.deleteById(id);
        log.info("Course deleted: {}", id);
        return id;
    }

    private CourseDto enrichWithTeacher(CourseDto dto, String teacherId) {
        try {
            dto.setTeacherName(userService.detail(teacherId).getUserName());
        } catch (Exception e) {
            log.warn("Could not resolve teacher name for teacherId={}", teacherId);
            dto.setTeacherName("UNKNOWN");
        }
        return dto;
    }
}
