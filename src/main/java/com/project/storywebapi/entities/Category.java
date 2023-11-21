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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "category")
public class Category {
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
	
	@Column(name = "description")
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "categoryId", fetch = FetchType.LAZY)
	private List<Category_Story> products;
	
	
}
