package com.project.storywebapi.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter @Setter
public class Images {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "imageName")
	private String imageName;
	
	@Column(name = "imageUrl")
	private String imageUrl;
	
	@Column(name = "createdAt")
	private Date createdAt;
	
	@Column(name = "isDeleted", columnDefinition = "BOOLEAN")
	private Boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chapterId", nullable = false)
	private Chapter chapterId;
}
