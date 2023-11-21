package com.project.storywebapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storywebapi.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	Optional<Role> findByName (String name);
}
