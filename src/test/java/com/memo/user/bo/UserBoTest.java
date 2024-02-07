package com.memo.user.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UserBoTest {

	@Autowired
	UserBo userBO;
	
	@Transactional // rollback 기능 (다시 돌이키는것)
	@Test
	void 유저추가테스트() {
		log.info("****유저추가 테스트 시작");
		userBO.addUser("test222", "비번테스트222", "테스트222", "테스트222@test.com");
	}

}
