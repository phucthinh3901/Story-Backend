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

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationWalletChapterDto;
import com.project.storywebapi.dto.UserDto;
import com.project.storywebapi.dto.WalletChapterCreateDto;
import com.project.storywebapi.dto.WalletHistoryCreateDto;
import com.project.storywebapi.entities.Chapter;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.entities.WalletChapter;
import com.project.storywebapi.entities.WalletHistory;
import com.project.storywebapi.entities.WalletHistoryType;
import com.project.storywebapi.payload.request.EmailPaymentNotificationRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.ChapterRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.repository.WalletChapterRepository;
import com.project.storywebapi.repository.WalletHistoryRepository;
import com.project.storywebapi.service.EmailService;
import com.project.storywebapi.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ChapterRepository chapterRepository;
	
	@Autowired
	WalletHistoryRepository walletHistoryRepository;
	
	@Autowired
	WalletChapterRepository walletChapterRepository;
	
	@Autowired
	EmailService emailService;
	
	@Override
	public ApiResponse createHistoryWallet(WalletHistoryCreateDto walletHistoryCreateDto) {
		
		ApiResponse apiResponse = new ApiResponse();
		
		if(walletHistoryCreateDto.getType() != WalletHistoryType.Deposit.toString() && walletHistoryCreateDto.getType() != WalletHistoryType.Withdraw.toString()) {
			apiResponse.setMessage("Hình thức giao dịch không hợp lệ");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		if(walletHistoryCreateDto.getAmount() <= 0) {
			apiResponse.setMessage("Số dư không đủ");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		User user = userRepository.findByIdAndIsDeletedAndVerified(walletHistoryCreateDto.getUserId());
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		if(walletHistoryCreateDto.getType() == WalletHistoryType.Withdraw.toString() && walletHistoryCreateDto.getAmount() > user.getAmount()) {
			apiResponse.setMessage("Số dư không đủ");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		
		WalletHistory walletHistory = new WalletHistory();
		walletHistory.setUserId(user);
		walletHistory.setAmount(walletHistoryCreateDto.getAmount());
		walletHistory.setContent(walletHistoryCreateDto.getContent());
		walletHistory.setCurrency(walletHistoryCreateDto.getCurrency());
		walletHistory.setCreatedAt(new Date());
		walletHistory.setCreatedBy(user.getId());
		walletHistory.setType(walletHistoryCreateDto.getType());
		walletHistoryRepository.save(walletHistory);
		
//		if(walletHistoryCreateDto.getType().toString() == WalletHistoryType.Withdraw.toString()) {
//			user.setAmount(user.getAmount() - walletHistoryCreateDto.getAmount());
//		}
		
		if(walletHistoryCreateDto.getType().toString() == WalletHistoryType.Deposit.toString()){
			user.setAmount(user.getAmount() + walletHistoryCreateDto.getAmount());
		}

		userRepository.save(user);
		
		EmailPaymentNotificationRequest email = new EmailPaymentNotificationRequest();
		email.setUserName(user.getUsername());
		email.setAmount(walletHistoryCreateDto.getAmount());
		email.setContent(walletHistoryCreateDto.getContent());
		email.setCreateAt(walletHistory.getCreatedAt());
		email.setToEmail(user.getEmail());
		
		emailService.sendMailUPpgradeRank(email);
		
		apiResponse.setMessage("Thêm mới thành công.");
		apiResponse.setSuccess(true);
		apiResponse.setData(walletHistory);
		return apiResponse;
	}

	@Override
	public ApiResponse createWalletChapter(WalletChapterCreateDto walletChapterCreateDto) {
		ApiResponse apiResponse = new ApiResponse();
			
		User user = userRepository.findByIdAndIsDeletedAndVerified(walletChapterCreateDto.getUserId());
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		List<Integer> listChapters = new ArrayList<Integer>();
		for(Integer chapter :  walletChapterCreateDto.getChapterId()) {
			listChapters.add(chapter);
		}
		List<Chapter> findChapter = chapterRepository.findAllById(listChapters);
		if(findChapter.size() != listChapters.size()) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		
		Double totalPrice = 0d;
		for(Chapter chapter : findChapter) {
			totalPrice += chapter.getPrice();
		}
		if(user.getAmount() < totalPrice) {
			apiResponse.setMessage("Số dư không đủ");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		
		List<WalletChapter> walletChapters = new ArrayList<WalletChapter>();
		WalletChapter walletChapter = null; 
		for(Chapter chapter : findChapter) {
			walletChapter = new WalletChapter();
			walletChapter.setUserId(user);
			walletChapter.setChapterId(chapter);
			walletChapter.setPrice(chapter.getPrice());
			walletChapter.setCurrency(chapter.getCurrency());
			walletChapter.setCreatedAt(new Date());
			walletChapter.setCreatedBy(user.getId());
			walletChapters.add(walletChapter);	
		}
		walletChapterRepository.saveAll(walletChapters);
		user.setAmount(user.getAmount() - totalPrice);
		userRepository.save(user);
		
		apiResponse.setMessage("Thêm mới thành công.");
		apiResponse.setSuccess(true);
		apiResponse.setData(walletChapters);
		
		return apiResponse;
	}

	@Override
	public ApiResponse getWalletHistoryByUser(UserDto userDto) {
		
		ApiResponse apiResponse = new ApiResponse();
		
		List<WalletHistory> walletHistory = walletHistoryRepository.findByUserId(userDto.getId());
		for(WalletHistory item : walletHistory) {
			item.setType(WalletHistoryType.valueOf(item.getType()).toString());	
		}
		apiResponse.setData(walletHistory);;
		apiResponse.setSuccess(true);	
		return apiResponse;
	}

	public Page<PaginationWalletChapterDto> convertListToPage(List<PaginationWalletChapterDto> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;

	    List<PaginationWalletChapterDto> pageList;

	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}
	
	@Override
	public Page<PaginationWalletChapterDto> paginationWalletChapter(PaginationDto paginationDto) {
		WalletChapterSpecificationImpl specification = new WalletChapterSpecificationImpl(paginationDto);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
		Page<WalletChapter> result = walletChapterRepository.findAll(specification, pageable);
		
		PaginationWalletChapterDto dto = null;
		List<PaginationWalletChapterDto> paginationWalletChapterDtos = new ArrayList<PaginationWalletChapterDto>();
		for(WalletChapter walletChapter : result.getContent()) {
				dto = new PaginationWalletChapterDto();
				dto.setChapterId(walletChapter.getChapterId().getId());
				dto.setChapterName(walletChapter.getChapterId().getName());
				dto.setChapterNumber(walletChapter.getChapterId().getChapterNumber());
				dto.setStoryId(walletChapter.getChapterId().getStoryId().getId());
				dto.setStoryName(walletChapter.getChapterId().getStoryId().getName());	
				dto.setCreateAt(walletChapter.getCreatedAt());
				dto.setCreateBy(walletChapter.getCreatedBy());
				dto.setUpdateAt(walletChapter.getUpdatedAt());
				dto.setUpdateBy(walletChapter.getUpdatedBy());;
				paginationWalletChapterDtos.add(dto);
		}
		Page<PaginationWalletChapterDto> page = convertListToPage(paginationWalletChapterDtos, pageable);
		return page;
	}
}
