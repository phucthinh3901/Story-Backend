package com.project.storywebapi.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.SearchCondition;
import com.project.storywebapi.entities.Favorite;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FavoriteSpecificationImpl implements Specification<Favorite>{
	private static final long serialVersionUID = -722482137536703849L;

	private PaginationDto data;

	@Override
	public Predicate toPredicate(Root<Favorite> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		SearchCondition item = data.getWhere();
		
		if(item.getIsDeleted() != null) {
			Predicate isDelete = cb.equal(root.get("isDeleted"), item.getIsDeleted());
			predicates.add(isDelete);
		}
		if (item.getUserId() != null) {
			Predicate userId = cb.like(root.get("userId"), "%"+item.getUserId()+"%");
			 predicates.add(userId);
		}
	
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
	
}
