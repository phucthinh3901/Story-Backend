package com.project.storywebapi.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.SearchCondition;
import com.project.storywebapi.entities.WalletChapter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WalletChapterSpecificationImpl implements Specification<WalletChapter>{
	
	private static final long serialVersionUID = -722482137536703849L;

	private PaginationDto data;
	
	@Override
	public Predicate toPredicate(Root<WalletChapter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
List<Predicate> predicates = new ArrayList<>();
		
		SearchCondition item = data.getWhere();
		
		if (item.getIsDeleted() != null) {
			Predicate isDeleted = cb.equal(root.get("isDeleted"), item.getIsDeleted());
			 predicates.add(isDeleted);
		}
		if (item.getUserId() != null) {
			Predicate storyId = cb.like(root.get("userId"), "%"+item.getUserId()+"%");
			 predicates.add(storyId);
		}
		if (item.getStoryId() != null) {
			Predicate storyId = cb.like(root.get("storyId"), "%"+item.getStoryId()+"%");
			 predicates.add(storyId);
		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
