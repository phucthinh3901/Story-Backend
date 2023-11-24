package com.project.storywebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.WalletHistory;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Integer>{

	@Query("From WalletHistory wh where wh.userId.id = :id ORDER BY wh.createdAt DESC")
	List<WalletHistory> findByUserId(@Param("id") Integer id);
}
