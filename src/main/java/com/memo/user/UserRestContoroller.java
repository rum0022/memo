package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserRestContoroller {

	@RequestMapping("/is-duplicated-id") // 겟, 포스트이면 리퀘스트 쓰기
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		//DB조회 - select
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("is-duplicated-id", true);
		return result;
	}
}
