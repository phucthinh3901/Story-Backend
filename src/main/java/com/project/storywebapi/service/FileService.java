package com.project.storywebapi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.storywebapi.dto.FileUploadDto;

public interface FileService {
	public List<FileUploadDto> uploadFiles(MultipartFile[] files);
}
