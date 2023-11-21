package com.project.storywebapi.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.storywebapi.dto.ChapterCreateDto;
import com.project.storywebapi.dto.ChapterUpdataDto;
import com.project.storywebapi.dto.FileUploadDto;
import com.project.storywebapi.dto.FilterOneDto;
import com.project.storywebapi.dto.PaginationChapterDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.entities.Chapter;
import com.project.storywebapi.entities.Images;
import com.project.storywebapi.entities.Story;
import com.project.storywebapi.payload.request.ActiveChapterRequest;
import com.project.storywebapi.payload.request.GetChapterByIdRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.payload.response.ChapterResponse;
import com.project.storywebapi.repository.ChapterRepository;
import com.project.storywebapi.repository.ImagesRepository;
import com.project.storywebapi.repository.StoryRepository;
import com.project.storywebapi.service.ChapterService;
import com.project.storywebapi.service.FileService;

@Service
public class ChapterServiceImpl implements ChapterService{

	@Autowired
	ChapterRepository chapterRepository;
	
	@Autowired
	StoryRepository storyRepository;
	
	@Autowired
	FileService storageService;
	
	@Autowired
	ImagesRepository imagesRepository;
	
	@Override
	public ApiResponse createData(MultipartFile[] files ,ChapterCreateDto chapterCreateDto) {
		
		ApiResponse apiResponse = new ApiResponse();
		ChapterResponse chapterResponse = new ChapterResponse();
		Chapter chapter = null;
		Images img = null;
		List<Images> images = new ArrayList<>(); 
		List<String> imageName = new ArrayList<String>();
		if(chapterCreateDto == null) {
			apiResponse.setMessage("Data Request is Null!.");
			return apiResponse;
		}
		
		Story story = storyRepository.findOneByIdAndType(chapterCreateDto.getStoryId());
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy truyện");
			return apiResponse;
		}
		
		chapter = chapterRepository.findByChapterNumberAndStoryId(chapterCreateDto.getChapterNumber(), chapterCreateDto.getStoryId());
		if(chapter != null) {
			apiResponse.setMessage("Số thứ tự chapter đã tồn tại");
			return apiResponse;
		}
		
		chapter = new Chapter();
		chapter.setChapterNumber(chapterCreateDto.getChapterNumber());
		chapter.setContent(chapterCreateDto.getContent());	
		chapter.setName(chapterCreateDto.getName());
		chapter.setStoryId(story);
		chapter.setViewCount(0);
		
		chapter.setCreatedAt(new Date());
		chapter.setIsDeleted(false);
		chapterRepository.save(chapter);
		if(files != null) {
			List<FileUploadDto> fileUploadDtos = storageService.uploadFiles(files);
			for(FileUploadDto fileUploadDto : fileUploadDtos) {
				img = new Images();
				img.setCreatedAt(new Date());
				img.setImageName(fileUploadDto.getFileName());
				img.setImageUrl(fileUploadDto.getFileUrl());
				img.setChapterId(chapter);
				images.add(img);	
				imageName.add(img.getImageUrl());
			}
		}
		imagesRepository.saveAll(images);
		
		chapterResponse.setId(chapter.getId());
		chapterResponse.setCreateAt(chapter.getCreatedAt());
		chapterResponse.setCreateBy(chapter.getCreatedBy());
		chapterResponse.setUpdateAt(chapter.getUpdatedAt());
		chapterResponse.setUpdateBy(chapter.getUpdatedBy());
		chapterResponse.setIsDelete(chapter.getIsDeleted());
		chapterResponse.setChapterNumber(chapter.getChapterNumber());
		chapterResponse.setName(chapter.getName());
		chapterResponse.setContent(chapter.getContent());
		chapterResponse.setViewCount(chapter.getViewCount());
		chapterResponse.setStoryId(chapter.getStoryId().getId());
		chapterResponse.setListImage(imageName);
		
	
		apiResponse.setData(chapterResponse);
		apiResponse.setSuccess(true);
		apiResponse.setMessage("Thêm mới thành công.");
		return apiResponse;
	}


	@Override
	public ApiResponse updateData(ChapterUpdataDto chapterUpdataDto) {
		
		Chapter chapter = chapterRepository.findById(chapterUpdataDto.getId()).orElse(null);
		ApiResponse apiResponse = new ApiResponse();
		if(chapter == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;			
		}
		if(!chapter.getChapterNumber().equals(chapterUpdataDto.getChapterNumber())){
			apiResponse.setMessage("Số thứ tự chapter đã tồn tại");
			return apiResponse;
		}
		Story story = storyRepository.findOneByIdAndType(chapterUpdataDto.getStoryId());
		
		chapter.setChapterNumber(chapterUpdataDto.getChapterNumber());
		chapter.setContent(chapterUpdataDto.getContent());
		chapter.setUpdatedAt(new Date());
		chapter.setStoryId(story);
		apiResponse.setData(chapter);
		apiResponse.setMessage("Cập nhật thành công.");
		apiResponse.setSuccess(true);
		return apiResponse;
	}


	@Override
	public ApiResponse update_active(ActiveChapterRequest filter) {
		ApiResponse apiResponse = new ApiResponse();
		List<Images> Images = new ArrayList<Images>();
		Chapter chapter = chapterRepository.findById(filter.getId()).orElse(null);
		if(chapter.getIsDeleted() == true) {
			apiResponse.setMessage("Truyện đã bị ngưng hoạt động");
			return apiResponse;
		}		
		else{
			List<Images> listImage = imagesRepository.findByImageIdAndIsDelete(filter.getId(),filter.getIsDel());
			for(Images image : listImage) {
				image.setIsDeleted(!filter.getIsDel());
				Images.add(image);
			}
			imagesRepository.saveAll(Images);
			
			chapter.setIsDeleted(!filter.getIsDel());
			chapterRepository.save(chapter);
			apiResponse.setData(chapter);
			apiResponse.setMessage("Cập nhật trạng thái thành công.");
			apiResponse.setSuccess(true);
		}	
		return apiResponse;
	}


	@Override
	public ApiResponse getChapterByStory(FilterOneDto filter) {
		
		ApiResponse apiResponse = new ApiResponse();
		
		List<Chapter> chapters = chapterRepository.findChaptersByStoryIdAndIsDeleteFalseAndNumChapterAsc(filter.getId());
		List<Chapter> getChapters = new ArrayList<Chapter>();	
		for(Chapter chapter : chapters ) {
			getChapters.add(chapter);
		}
		apiResponse.setData(getChapters);
		return apiResponse;
	}


	@Override
	public ApiResponse getChapterById(FilterOneDto filterOneDto) {
		GetChapterByIdRequest byIdRequest = new GetChapterByIdRequest();
		Chapter chapter = null;
		ApiResponse apiResponse = new ApiResponse();
		
		chapter = chapterRepository.findChapterByIdAndIsDelete(byIdRequest.getId());
		if(chapter == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		List<Chapter> lstChapter = chapterRepository.findChaptersByStoryIdAndIsDeleteFalseAndNumChapterAsc(chapter.getStoryId().getId());
		
		Story story = storyRepository.findOneByIdAndType(chapter.getStoryId().getId());
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		int length = 0;
		length = lstChapter.size();
		byIdRequest.setNextId(null);
		byIdRequest.setPrevId(null);
		for(int i = 0; i < length; i++) {
			if (lstChapter.get(i).getId().equals(chapter.getId())) {
				byIdRequest.setPrevId(i > 0 ? lstChapter.get(i-1).getId() : null);
				byIdRequest.setNextId(i < length - 1 ? lstChapter.get(i + 1).getId() : null);
				break;
			}
			
		}
		byIdRequest.setChapterNumber(chapter.getChapterNumber());
		byIdRequest.setContent(chapter.getContent());
		byIdRequest.setCreatedAt(chapter.getCreatedAt());
		byIdRequest.setId(chapter.getId());
		byIdRequest.setIsDeleted(chapter.getIsDeleted());
		byIdRequest.setName(chapter.getName());
		byIdRequest.setStoryId(chapter.getStoryId().getId());
		byIdRequest.setStoryName(chapter.getStoryId().getName());
		byIdRequest.setStoryType(chapter.getStoryId().getType().toString());
		byIdRequest.setUpdatedAt(chapter.getUpdatedAt());
		byIdRequest.setViewCount(chapter.getViewCount());

		
		apiResponse.setData(byIdRequest);
		return apiResponse;
	}


	@Override
	public ApiResponse plusViewChapter(FilterOneDto filterOneDto) {
		ApiResponse apiResponse = new ApiResponse();
		Chapter chapter = chapterRepository.findChapterByIdAndIsDelete(filterOneDto.getId());
		
		if(chapter == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		chapter.setViewCount(chapter.getViewCount() + 1);
		chapterRepository.save(chapter);
		apiResponse.setData(chapter);
		apiResponse.setSuccess(true);
		return apiResponse;
	}


	@Override
	public ApiResponse getPrintChapter(FilterOneDto filterOneDto) {
		ApiResponse apiResponse = new ApiResponse();
		Story story = storyRepository.findOneByIdAndType(filterOneDto.getId());		
		List<Chapter> chapters = chapterRepository.findChaptersByStoryIdAndIsDeleteFalseAndNumChapterAsc(story.getId());	
		apiResponse.setData(chapters);	
		return apiResponse;
	}
	
	public Page<PaginationChapterDto> convertListToPage(List<PaginationChapterDto> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;
	    List<PaginationChapterDto> pageList;
	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}

	@Override
	public Page<PaginationChapterDto> paginationChapter(PaginationDto paginationDto) {
		
		ChapterSpecificationImpl specification = new ChapterSpecificationImpl(paginationDto);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("chapterNumber"));
		Page<Chapter> result = chapterRepository.findAll(specification, pageable);
		
		PaginationChapterDto paginationChapterDto = null;
		List<PaginationChapterDto> chapterDtos  = new ArrayList<PaginationChapterDto>();
	
		
		for(Chapter chapter : result.getContent()) {
			paginationChapterDto = new PaginationChapterDto();
			paginationChapterDto.setId(chapter.getId());
			paginationChapterDto.setCreateAt(chapter.getCreatedAt());
			paginationChapterDto.setCreateBy(chapter.getCreatedBy());
			paginationChapterDto.setUpdateAt(chapter.getUpdatedAt());
			paginationChapterDto.setUpdateBy(chapter.getUpdatedBy());
			paginationChapterDto.setIsDelete(chapter.getIsDeleted());
			paginationChapterDto.setName(chapter.getName());
			paginationChapterDto.setChapterNumber(chapter.getChapterNumber());
			paginationChapterDto.setContent(chapter.getContent());
			paginationChapterDto.setViewCount(chapter.getViewCount());
			paginationChapterDto.setStoryId(chapter.getStoryId().getId());
			chapterDtos.add(paginationChapterDto);
		}

		Page<PaginationChapterDto> page = convertListToPage(chapterDtos, pageable);
		return page;
	}
	
	
	
}
