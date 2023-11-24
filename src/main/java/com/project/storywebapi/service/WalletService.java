package com.project.storywebapi.service;

import org.springframework.data.domain.Page;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationWalletChapterDto;
import com.project.storywebapi.dto.UserDto;
import com.project.storywebapi.dto.WalletChapterCreateDto;
import com.project.storywebapi.dto.WalletHistoryCreateDto;
import com.project.storywebapi.payload.response.ApiResponse;

public interface WalletService {
	
	ApiResponse createHistoryWallet(WalletHistoryCreateDto walletHistoryCreateDto);
	
	ApiResponse createWalletChapter(WalletChapterCreateDto walletChapterCreateDto);
	
	ApiResponse getWalletHistoryByUser(UserDto userDto);
	
	Page<PaginationWalletChapterDto> paginationWalletChapter(PaginationDto paginationDto);
}
