package com.project.storywebapi.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "FeedbackCommentChapter")
public class FeedbackCommentChapter {

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
	
	@Column(name = "content")
	private String content;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commentChapterId")
	private CommentChapter commentChapter;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User userId;
	
	
}