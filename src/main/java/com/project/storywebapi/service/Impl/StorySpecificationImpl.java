package com.project.storywebapi.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.SearchCondition;
import com.project.storywebapi.entities.Story;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StorySpecificationImpl implements Specification<Story>{
	
	private static final long serialVersionUID = -722482137536703849L;

	private PaginationDto data;
	
	@Override
	public Predicate toPredicate(Root<Story> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		SearchCondition item = data.getWhere();
		
		if(item.getIsDeleted() != null) {
			Predicate isDelete = cb.equal(root.get("isDeleted"), item.getIsDeleted());
			predicates.add(isDelete);
		}
		if(item.getName() != null) {
			Predicate name = cb.like(root.get("name"), "%"+item.getName()+"%");
			 predicates.add(name);
		}
		if(item.getFinished() != null)	{
			Predicate finished = cb.like(root.get("finished"), "%"+item.getFinished()+"%");
			 predicates.add(finished);
		}
		if(item.getTypeStory() != null)	{
			Predicate type = cb.like(root.get("type"), "%"+item.getTypeStory()+"%");
			 predicates.add(type);
		}
		if(item.getListCategoryId() != null)	{
			Predicate listCategoryId = cb.like(root.get("storyId"), "%"+item.getListCategoryId()+"%");
			 predicates.add(listCategoryId);
		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
