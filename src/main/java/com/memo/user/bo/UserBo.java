package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.entity.UserEntity;
import com.memo.user.repository.UserRepository;

@Service
public class UserBo {
	
	@Autowired
	private UserRepository userRepository;
	
	// input : loginId     output:UserEntity (있거나 or null)
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}

//	UserEntity userEntity = UserEntity.builder()
//			.loginId(loginId)
//			.password(password)
//			.name(name)
//			.email(email)
//			.build()
//	
	
	// input : 파라미터 4개  output :  유저엔티티 받지만 가공해서 Integer id(pk) 
	public Integer addUser(String loginId, String password, String name, String email) {
		UserEntity userEntity = userRepository.save(
					UserEntity.builder()
						.loginId(loginId)
						.password(password)
						.name(name)
						.email(email)
						.build()
				);
		return userEntity == null ? null : userEntity.getId();
	}
	
	// input: loginId, password    output: userEntity
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}
}
