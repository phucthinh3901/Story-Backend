package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationWalletChapterDto;
import com.project.storywebapi.dto.UserDto;
import com.project.storywebapi.dto.WalletChapterCreateDto;
import com.project.storywebapi.dto.WalletHistoryCreateDto;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.WalletService;

@RestController
@RequestMapping("wallet")
public class WalletController {
	
	@Autowired
	WalletService walletService;
	
	@PostMapping("/create_wallet_history")
	private ApiResponse createWalletHistory(@RequestBody WalletHistoryCreateDto walletHistoryCreateDto ) {
		return walletService.createHistoryWallet(walletHistoryCreateDto);
	}
	
	@PostMapping("/create_wallet_chapter")
	private ApiResponse createWalletChapter (@RequestBody WalletChapterCreateDto walletChapterCreateDto) {
		return walletService.createWalletChapter(walletChapterCreateDto);
	}
	
	@PostMapping("/get_wallet_history")
	private ApiResponse getWalletHistoryByUser(@RequestBody UserDto userDto) {
		return walletService.getWalletHistoryByUser(userDto);
	}
	
	@PostMapping("/pagination")
	public Page<PaginationWalletChapterDto> paginationChapter(@RequestBody PaginationDto paginationDto){
		return walletService.paginationWalletChapter(paginationDto);
	}
	
}
