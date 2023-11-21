package com.project.storywebapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter		
public class FileUploadDto {
	 private String fileName;
	 
	 private String fileUrl;
}
