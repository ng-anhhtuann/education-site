package vn.com.eduhub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.VideoDto;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.entity.Video;
import vn.com.eduhub.exception.ResourceNotFoundException;
import vn.com.eduhub.exception.ValidationException;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.VideoRepository;
import vn.com.eduhub.service.ICourseService;
import vn.com.eduhub.service.IVideoService;
import vn.com.eduhub.service.SearchHelper;
import vn.com.eduhub.utils.CommonConstant;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Criteria;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoServiceImpl implements IVideoService {

    private final ModelMapper mapper;
    private final VideoRepository videoRepository;
    private final CourseRepository courseRepository;
    private final ICourseService courseService;
    private final SearchHelper searchHelper;

    @Override
    public VideoDto edit(VideoDto dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            return createVideo(dto);
        }
        return updateVideo(dto);
    }

    private VideoDto createVideo(VideoDto dto) {
        Video video = mapper.map(dto, Video.class);
        if (!courseRepository.existsById(video.getCourseId())) {
            throw new ResourceNotFoundException(CommonConstant.COURSE_NOT_FOUND);
        }
        video.setId(UUID.randomUUID().toString() + System.currentTimeMillis());
        video.setCreatedDate(Instant.now());
        videoRepository.insert(video);
        log.info("Video created: {}", video.getTitle());
        return enrichWithCourse(mapper.map(video, VideoDto.class), video.getCourseId());
    }

    private VideoDto updateVideo(VideoDto dto) {
        Video video = videoRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.FILE_NOT_FOUND));

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new ValidationException(CommonConstant.EMPTY_TITLE);
        }

        video.setTitle(dto.getTitle());
        if (dto.getDescription() != null) video.setDescription(dto.getDescription());
        video.setUpdatedDate(Instant.now());
        videoRepository.save(video);
        log.info("Video updated: {}", video.getId());
        return enrichWithCourse(mapper.map(video, VideoDto.class), video.getCourseId());
    }

    @Override
    public PagedResponse<VideoDto> search(CommonSearchReq req) {
        SearchHelper.SearchResult<Video> result = searchHelper.search(req, Video.class, (params, criteriaList) -> {
            for (var entry : params.entrySet()) {
                if ("title".equals(entry.getKey()) && entry.getValue() instanceof String) {
                    criteriaList.add(Criteria.where("title").regex(String.valueOf(entry.getValue()), "i"));
                } else {
                    criteriaList.add(Criteria.where(entry.getKey()).is(entry.getValue()));
                }
            }
        });

        var dtos = result.items().stream()
                .map(v -> enrichWithCourse(mapper.map(v, VideoDto.class), v.getCourseId()))
                .collect(Collectors.toList());

        return new PagedResponse<>(result.total(), dtos);
    }

    @Override
    public VideoDto detail(String id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.FILE_NOT_FOUND));
        return enrichWithCourse(mapper.map(video, VideoDto.class), video.getCourseId());
    }

    @Override
    public String delete(String id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException(CommonConstant.FILE_NOT_FOUND);
        }
        videoRepository.deleteById(id);
        log.info("Video deleted: {}", id);
        return id;
    }

    private VideoDto enrichWithCourse(VideoDto dto, String courseId) {
        try {
            dto.setCourseName(courseService.detail(courseId).getTitle());
        } catch (Exception e) {
            log.warn("Could not resolve course name for courseId={}", courseId);
            dto.setCourseName("UNKNOWN");
        }
        return dto;
    }
}
