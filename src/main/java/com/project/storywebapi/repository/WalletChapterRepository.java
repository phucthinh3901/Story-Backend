package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.project.storywebapi.entities.WalletChapter;

public interface WalletChapterRepository extends JpaRepository<WalletChapter, Integer>,JpaSpecificationExecutor<WalletChapter>{

}
