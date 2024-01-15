package com.memo.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	// null 이거나 UserEntity(단건) 돌려준다
	public UserEntity findByLoginId(String loginId);
	
	// null 이거나 UserEntity(단건) 돌려준다
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
}
