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
import vn.com.eduhub.dto.master.VideoDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.entity.Video;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.VideoRepository;
import vn.com.eduhub.service.ICourseService;
import vn.com.eduhub.service.IVideoService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements IVideoService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    ICourseService courseService;

    /**
     * Thêm mới hoặc chỉ thay đổi title và description
     */
    @Override
    public Video edit(VideoDto dto) throws Exception {
        if (dto.getId() == null || dto.getId().isEmpty() || dto.getId().isBlank()) {
            Video video = mapper.map(dto, Video.class);
            Optional<Course> courseOptional = courseRepository.findById(video.getCourseId());
            if (courseOptional.isEmpty())
                throw new Exception(CommonConstant.COURSE_NOT_FOUND);
            video.setId(String.valueOf(UUID.randomUUID()).concat(String.valueOf(System.currentTimeMillis())));
            video.setCreatedDate(null);
            video.setCreatedDate(new Date());
            videoRepository.insert(video);
            return video;
        } else {
            Optional<Video> imageOptional = videoRepository.findById(dto.getId());
            if (imageOptional.isPresent()) {
                Video video = imageOptional.get();
                video.setUpdatedDate(new Date());
                if (dto.getTitle() != null || !dto.getTitle().trim().isEmpty())
                    video.setTitle(dto.getTitle());
                else
                    throw new Exception(CommonConstant.EMPTY_TITLE);

                if (dto.getDescription() != null || !dto.getDescription().trim().isEmpty())
                    video.setDescription(dto.getDescription());
                else
                    throw new Exception(CommonConstant.EMPTY_DESCRIPTION);
                videoRepository.save(video);
                return video;
            } else {
                throw new Exception(CommonConstant.FILE_NOT_FOUND);
            }
        }
    }

    /**
     * Tìm kiếm danh sách theo course_id và title
     */
    @Override
    public ObjectDataRes<Video> getList(CommonSearchReq req) {
        return null;
    }

    @Override
    public VideoDto detail(String id) throws Exception {
        Optional<Video> videoOptional = videoRepository.findById(id);
        if (videoOptional.isEmpty()) {
            throw new Exception(CommonConstant.FILE_NOT_FOUND);
        }
        return mapper.map(videoOptional.get(), VideoDto.class);
    }

    @Override
    public String delete(String id) throws Exception {
        Optional<Video> videoOptional = videoRepository.findById(id);
        if (videoOptional.isEmpty()) {
            throw new Exception(CommonConstant.FILE_NOT_FOUND);
        }
        try {
            videoRepository.deleteById(id);
            return id;
        } catch (Exception ex) {
            throw new Exception(CommonConstant.PROCESS_FAIL);
        }
    }

    @Override
    public ObjectDataRes<VideoDto> search(CommonSearchReq req) {
        List<Video> listData = new ArrayList<>();
        List<VideoDto> dtoList;

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
            listData = mongoTemplate.find(query, Video.class);
        }
        if (req.getSearchType().equals("FIELD") && req.getParams() != null) {
            Criteria criteria = new Criteria();
            List<Criteria> criteriaList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : req.getParams().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if ("title".equals(key) && value instanceof String) {
                    criteriaList.add(Criteria.where(key).regex(String.valueOf(value), "i"));
                } else {
                    criteriaList.add(Criteria.where(key).is(value));
                }

                if (!criteriaList.isEmpty()) {
                    criteria.andOperator(criteriaList.toArray(new Criteria[0]));
                    query.addCriteria(criteria);
                    listData = mongoTemplate.find(query, Video.class);
                }
            }
        }

        dtoList = listData.stream()
            .map(video -> {
                VideoDto videoDto = mapper.map(video, VideoDto.class);
                try {
                    videoDto.setCourseName(courseService.detail(video.getCourseId()).getTitle());
                } catch (Exception e) {
                    e.printStackTrace();
                    videoDto.setCourseName("UNKNOWN");
                }
                return videoDto;
            })
            .collect(Collectors.toList());

        return new ObjectDataRes<>(dtoList.size(), dtoList);
    }
}
