package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBo;
	
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required=false) MultipartFile file,
			HttpSession session) { // userId 가져와야하니까.
		
		// 글쓴이 번호 - session에 있는 userId를 꺼낸다.
		int userId = (int)session.getAttribute("userId"); // 글쓰기를 했는데 널포인트 에센셜???이나면 로그인이 풀려있는것 일부러 에러나게 하려고 int로 함
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// db insert
		postBo.addPost(userId, userLoginId, subject, content, file);
		//응답값
		Map<String, Object> result = new HashMap<>();
		
			result.put("code", 200);
			result.put("result", "성공");
		
		return result;
	}
}
