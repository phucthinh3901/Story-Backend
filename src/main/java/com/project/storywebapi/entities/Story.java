package com.project.storywebapi.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "story")
public class Story {

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
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "otherName")
	private String otherName;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "finished")
	private Boolean finished;
	
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private TypeStory type;
	
	@Column(name = "avatar")
	private String avatar;
	
	@JsonIgnore
	@OneToMany(mappedBy = "storyId", fetch = FetchType.LAZY)
	private List<Category_Story> storyId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "storyId", fetch = FetchType.LAZY)
	private List<Favorite> favorites;
	
	@JsonIgnore
	@OneToMany(mappedBy = "storyId", fetch = FetchType.LAZY)
	private List<Comment> commets;
	
	@JsonIgnore
	@OneToMany(mappedBy = "storyId", fetch = FetchType.LAZY)
	private List<Chapter> chapters;
	
	
}

