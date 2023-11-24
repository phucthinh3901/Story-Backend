package com.project.storywebapi.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "chapter")
public class Chapter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "createdAt")
	private Date createdAt;
	
	@Column(name = "createdBy")
	private Integer createdBy;
	
	@Column(name = "updatedAt")
	private Date updatedAt;
	
	@Column(name = "updatedBy")
	private Integer updatedBy;
	
	@Column(name = "isDeleted", columnDefinition = "BOOLEAN")
	private Boolean isDeleted;
	
	@Column(name = "chapterNumber")
	private Float chapterNumber;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "viewCount")
	private Integer viewCount;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "price")
	private Double price;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storyId")
	private Story storyId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "chapterId", fetch = FetchType.LAZY)
	private List<History> chapterId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY)
	private List<CommentChapter> commentChapters;
	
	@JsonIgnore
	@OneToMany(mappedBy = "chapterId", fetch = FetchType.LAZY)
	private List<Images> images;
	
	@JsonIgnore
	@OneToMany(mappedBy = "chapterId", fetch = FetchType.LAZY)
	private List<WalletChapter> walletChapters;
}
