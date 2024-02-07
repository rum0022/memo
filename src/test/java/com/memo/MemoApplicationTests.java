package com.memo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//SpringBootTest
class MemoApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	//@Test
	void 더하기테스트() {
		log.info("$$$$$$$$$$$$$더하기 테스트 시작");
		int a = 10;
		int b = 20;
		
		assertEquals(30, a + b);
	}
	
	@Test
	void 비어있는지확인() {//ObjectUtils 사용
		//List<Integer> list = new ArrayList<>(); // []
		List<Integer> list = null; // []
		if (ObjectUtils.isEmpty(list)) { //널이거나 비어있으면 엠티가 나옴
			log.info("list is empty.");
		}
		
		//String str = null;
		String str = "";
		if (ObjectUtils.isEmpty(str)) {
			log.info("str is empty.");
		}
	}
	
	// 로그테이블(현황 통계 차트)
	// 강퇴, 탈퇴 등 안지우고 계속 쌓는것 (좋아요 등등)
	// 인덱스만 잘 쌓아두면됨
}
