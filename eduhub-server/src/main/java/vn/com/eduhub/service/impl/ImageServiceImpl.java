package vn.com.eduhub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.ImageDto;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.entity.Image;
import vn.com.eduhub.exception.ResourceNotFoundException;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.ImageRepository;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.ICourseService;
import vn.com.eduhub.service.IImageService;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.service.SearchHelper;
import vn.com.eduhub.utils.CommonConstant;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService {

    private final ModelMapper mapper;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ICourseService courseService;
    private final IUserService userService;
    private final SearchHelper searchHelper;

    @Override
    public ImageDto edit(ImageDto dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            return createImage(dto);
        }
        return updateImage(dto);
    }

    private ImageDto createImage(ImageDto dto) {
        Image img = mapper.map(dto, Image.class);
        if (Boolean.TRUE.equals(img.getIsAvatar())) {
            if (!userRepository.existsById(img.getOwnerId())) {
                throw new ResourceNotFoundException(CommonConstant.USER_NOT_FOUND);
            }
        } else {
            if (!courseRepository.existsById(img.getOwnerId())) {
                throw new ResourceNotFoundException(CommonConstant.COURSE_NOT_FOUND);
            }
        }
        img.setId(UUID.randomUUID().toString() + System.currentTimeMillis());
        img.setCreatedDate(Instant.now());
        imageRepository.insert(img);
        log.info("Image created: {}", img.getName());
        return enrichWithOwner(mapper.map(img, ImageDto.class), img);
    }

    private ImageDto updateImage(ImageDto dto) {
        Image img = imageRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.FILE_NOT_FOUND));

        if (dto.getName() != null) img.setName(dto.getName());
        if (dto.getUrl() != null) img.setUrl(dto.getUrl());
        img.setUpdatedDate(Instant.now());
        imageRepository.save(img);
        log.info("Image updated: {}", img.getId());
        return enrichWithOwner(mapper.map(img, ImageDto.class), img);
    }

    @Override
    public PagedResponse<ImageDto> search(CommonSearchReq req) {
        SearchHelper.SearchResult<Image> result = searchHelper.search(req, Image.class);
        var dtos = result.items().stream()
                .map(img -> enrichWithOwner(mapper.map(img, ImageDto.class), img))
                .collect(Collectors.toList());
        return new PagedResponse<>(result.total(), dtos);
    }

    @Override
    public ImageDto detail(String id) {
        Image img = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.FILE_NOT_FOUND));
        return enrichWithOwner(mapper.map(img, ImageDto.class), img);
    }

    @Override
    public String delete(String id) {
        if (!imageRepository.existsById(id)) {
            throw new ResourceNotFoundException(CommonConstant.FILE_NOT_FOUND);
        }
        imageRepository.deleteById(id);
        log.info("Image deleted: {}", id);
        return id;
    }

    private ImageDto enrichWithOwner(ImageDto dto, Image img) {
        try {
            if (Boolean.TRUE.equals(img.getIsAvatar())) {
                dto.setOwnerName(userService.detail(img.getOwnerId()).getUserName());
            } else {
                dto.setOwnerName(courseService.detail(img.getOwnerId()).getTeacherName());
            }
        } catch (Exception e) {
            log.warn("Could not resolve owner name for ownerId={}", img.getOwnerId());
            dto.setOwnerName("UNKNOWN");
        }
        return dto;
    }
}
