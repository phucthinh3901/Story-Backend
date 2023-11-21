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
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "createdAt")
	private Date createdAt;
	
	@Column(name = "createdBy")
	private Integer createdBy;
	
	@Column(name = "isDeleted", columnDefinition = "BOOLEAN")
	private Boolean isDeleted;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name = "verified", columnDefinition = "BOOLEAN")
	private Boolean verified;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<Favorite> favorites;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<Comment> histories;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	private Role roleId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<FeedbackComment> feedbackComments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<FeedbackCommentChapter> feedbackCommentChapters;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<CommentChapter> commentChapters;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<WalletChapter> walletChapters;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private List<WalletHistory> walletHistories;
}
