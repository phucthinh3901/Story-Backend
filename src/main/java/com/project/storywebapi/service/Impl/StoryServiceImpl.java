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

import com.project.storywebapi.dto.FileUploadDto;
import com.project.storywebapi.dto.GetStoryDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationStory;
import com.project.storywebapi.dto.StoryDto;
import com.project.storywebapi.entities.Category;
import com.project.storywebapi.entities.Category_Story;
import com.project.storywebapi.entities.Chapter;
import com.project.storywebapi.entities.Favorite;
import com.project.storywebapi.entities.Story;
import com.project.storywebapi.entities.TypeStory;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.request.UpdateStory;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.CategoryRepository;
import com.project.storywebapi.repository.CategoryStoryRepository;
import com.project.storywebapi.repository.StoryRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.service.FileService;
import com.project.storywebapi.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService{

	@Autowired	
	StoryRepository storyRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired 
	CategoryStoryRepository categoryStoryRepository;
	
	@Autowired
	FileService storageService;
	
	@Override
	public ApiResponse createStory(MultipartFile[] file, StoryDto storyDto) {
		ApiResponse apiResponse = new ApiResponse();
		User user = userRepository.findByUserIdAndIsDeleteFalse(storyDto.getUserId());
		if(user == null) {
			apiResponse.setMessage("Dữ liệu không tồn tại.");
			return apiResponse;
		}
		
		Story story = new Story();
		
		if(storyDto.getLstCategoryIds() == null){
			apiResponse.setMessage("Hãy chọn một danh mục");
			return apiResponse;
		}
		if(!storyDto.getType().equals(story.getType().comic.getLabel()) && !storyDto.getType().equals(story.getType().word.getLabel())) {
			apiResponse.setMessage("Loại truyện không hợp lệ");
			return apiResponse;
		}
		
		List<Category> cateStory = categoryRepository.findNameCategoryById(storyDto.getLstCategoryIds());
		if(cateStory != null && cateStory.size() != storyDto.getLstCategoryIds().size()) {
			{
			apiResponse.setMessage("Danh mục không tồn tại hoặc đã bị ngưng hoạt động");
			return apiResponse;
			}
		}
		story.setName(storyDto.getName());
		story.setAuthor(storyDto.getAuthor());
		
		story.setFinished(storyDto.getFinished());
		story.setContent(storyDto.getContent());
		story.setOtherName(storyDto.getOtherName());
		story.setCreatedAt(new Date());
		story.setIsDeleted(false);
		story.setCreatedBy(user.getId());
		story.setUpdatedAt(new Date());
	
		if(file != null) {
			List<FileUploadDto> imgResult = storageService.uploadFiles(file);
			story.setAvatar(imgResult.get(0).getFileName());
		}		
		if(storyDto.getType().equals(TypeStory.comic)) {
			story.setType(TypeStory.comic);
		}else {
			story.setType(TypeStory.word);
		}
				
		story = storyRepository.save(story);
		
		Category_Story category_Story = new Category_Story();
		for(Category cate : cateStory) {
			category_Story.setCategoryId(cate);
			category_Story.setCreatedAt(new Date());
			category_Story.setStoryId(story);
			category_Story.setCreatedBy(user.getId());			
			categoryStoryRepository.save(category_Story);
		}
		apiResponse.setSuccess(true);
		apiResponse.setData(story);
		apiResponse.setMessage("Thêm mới thành công.");
       
		return apiResponse;
	}
	
	@Override
	public ApiResponse updateStory(UpdateStory updateStory) {
		ApiResponse apiResponse = new ApiResponse();
		
		Story story = storyRepository.findById(updateStory.getId()).orElse(null);
		
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		if(updateStory.getLstCategoryIds() == null || updateStory.getLstCategoryIds().size() == 0)	{
			apiResponse.setMessage("Hãy chọn một danh mục");
			return apiResponse;
		}
		if(updateStory.getType() == story.getType().comic.toString() || updateStory.getType() == story.getType().word.toString()) {
			apiResponse.setMessage("Loại truyện không hợp lệ");
			return apiResponse;
		}
		if(story.getIsDeleted() == true){
			apiResponse.setMessage("Truyện đã bị ngưng hoạt động");
			return apiResponse;
		}
		if(story.getStoryId() == null || story.getStoryId().size() != updateStory.getLstCategoryIds().size()){
			apiResponse.setMessage("Danh mục không tồn tại hoặc đã bị ngưng hoạt động");
			return apiResponse;
		}
		User user = userRepository.findByUserIdAndIsDeleteFalse(updateStory.getUserId());
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu.");
			return apiResponse;
		}
		story.setName(updateStory.getName());
		story.setAuthor(updateStory.getAuthor());
		story.setOtherName(updateStory.getOtherName());
		story.setFinished(updateStory.getFinished());
		story.setContent(updateStory.getContent());
		story.setType(story.getType());
		story.setAvatar(updateStory.getAvatar());
		story.setUpdatedAt(new Date());
		story.setUpdatedBy(user.getId());
		story = storyRepository.save(story);
		
		for(Integer cate : updateStory.getLstCategoryIds()) {
			Category_Story category_Story = new Category_Story();
			Category category = categoryRepository.findById(cate).orElse(null);
			category_Story.setCategoryId(category);
			category_Story.setUpdatedAt(new Date());
			category_Story.setStoryId(story);
			category_Story.setUpdatedBy(user.getId());			
			categoryStoryRepository.save(category_Story);
		}
		apiResponse.setData(story);
		apiResponse.setMessage("Cập nhật thành công.");
		apiResponse.setSuccess(true);
		return apiResponse;
	}	
	
	@Override
	public ApiResponse updateActive(ActiveRequest activeRequest) {
		ApiResponse apiResponse = new ApiResponse();
		Story story = storyRepository.findById(activeRequest.getId()).orElse(null);
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		
		Boolean newIsDelete = story.getIsDeleted();
		story.setIsDeleted(newIsDelete);
		storyRepository.save(story);
		apiResponse.setMessage("Cập nhật trạng thái thành công.");
		apiResponse.setSuccess(true);
		apiResponse.setData(story.getIsDeleted());
		return apiResponse;
	}
	
	@Override
	public ApiResponse getStory(ActiveRequest storyId) {
		
		GetStoryDto getStoryDto = new GetStoryDto();
		ApiResponse apiResponse = new ApiResponse();
		Story story = storyRepository.findOneByIdAndType(storyId.getId());
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		getStoryDto.setAuthor(story.getAuthor());
		getStoryDto.setId(story.getId());
		getStoryDto.setCreatedAt(story.getCreatedAt());
		getStoryDto.setUpdatedAt(story.getUpdatedAt());
		getStoryDto.setIsDeleted(story.getIsDeleted());
		getStoryDto.setName(story.getName());
		getStoryDto.setOtherName(story.getOtherName());
		getStoryDto.setAvatar(story.getAvatar());
		getStoryDto.setContent(story.getContent());
		getStoryDto.setFinished(story.getFinished());
		getStoryDto.setType(story.getType().toString());
		getStoryDto.setTypeName(story.getType().toString());;
		 
		Integer countCommentInt = Long.valueOf(story.getCommets().stream().count()).intValue();	
		Integer countFavorieInt = Long.valueOf(story.getFavorites().stream().count()).intValue();

		getStoryDto.setCommentCount(countCommentInt);
		getStoryDto.setFavoriteCount(countFavorieInt);
		
		List<Integer> cateStoryDtoIds = new ArrayList<Integer>();
		
		List<String> cateStoryNameDtoIds = new ArrayList<String>();
		
		for(Category_Story cateStoryId : story.getStoryId()) {
			cateStoryDtoIds.add(cateStoryId.getId());
			cateStoryNameDtoIds.add(cateStoryId.getCategoryId().getName());
		}
		getStoryDto.setLstCategoryIds(cateStoryDtoIds);
		getStoryDto.setLstCategoryName(cateStoryNameDtoIds);
		
		List<Integer> userIdFavorite = new ArrayList<Integer>();
		for(Favorite favorite : story.getFavorites()) {
			userIdFavorite.add(favorite.getUserId().getId());
		}
		getStoryDto.setLstUserIdFavorite(userIdFavorite);
		Integer countChapter = 0;
		for(Chapter chapter : story.getChapters()) {
			 countChapter += chapter.getViewCount();		 
		}
		getStoryDto.setTotalView(countChapter);
		apiResponse.setData(getStoryDto);
		apiResponse.setSuccess(true);

		return apiResponse;
	}

	
	public Page<PaginationStory> convertListToPage(List<PaginationStory> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;

	    List<PaginationStory> pageList;

	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}
	
	@Override
	public Page<PaginationStory> paginationStory(PaginationDto request) {
		
		StorySpecificationImpl specification = new StorySpecificationImpl(request);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("updatedAt")); 
		Page<Story> result = storyRepository.findAll(specification, pageable);
		
		List<PaginationStory> paginationStories = new ArrayList<PaginationStory>();
		PaginationStory dto = null;
		List<Integer> listCategoryId = null;
		List<String> listNameCategoryName = null;
		Integer count = 0;
		for(Story story : result.getContent()) {
			dto = new PaginationStory();
			
			dto.setId(story.getId());
			dto.setCreateAt(story.getCreatedAt());
			dto.setCreateBy(story.getCreatedBy());
			dto.setUpdateAt(story.getUpdatedAt());
			dto.setUpdateBy(story.getUpdatedBy());
			dto.setName(story.getName());
			dto.setOtherName(story.getOtherName());
			dto.setAuthor(story.getAuthor());
			dto.setAvatar(story.getAvatar());
			dto.setContent(story.getContent());
			dto.setType(story.getType().toString());
			dto.setIsDelete(story.getIsDeleted());
			dto.setFinished(story.getFinished());		
			listCategoryId = new ArrayList<Integer>();
			
			dto.setCommentCount(Long.valueOf(story.getCommets().stream().count()).intValue());
			dto.setFavoriteCount(Long.valueOf(story.getFavorites().stream().count()).intValue());
			dto.setChapterCount(Long.valueOf(story.getChapters().stream().count()).intValue());
			
			listCategoryId = new ArrayList<Integer>();
			listNameCategoryName = new ArrayList<String>();
			
			for(Category_Story category_Story : story.getStoryId()) {
				listCategoryId.add(category_Story.getId());
				listNameCategoryName.add(category_Story.getCategoryId().getName());
			}
			dto.setLstCategoryId(listCategoryId);
			dto.setLstCategoryName(listNameCategoryName);
			
			for(Chapter countView : story.getChapters()) {
				count += countView.getViewCount();
			}
			dto.setTotalView(count);
			paginationStories.add(dto);
		}
		Page<PaginationStory> page = convertListToPage(paginationStories, pageable);
		
		return page;
	}

}














