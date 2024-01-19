package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private PostBO postBO;

	/**
	 * 글쓰기화면
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/post-list-view")
	public String postListView(Model model, HttpSession session) {
		// 로그인이 되있어야하므로 권한검사도하기 (로그인여부조회)
		Integer userId = (Integer)session.getAttribute("userId");  // object로 뺄수 없으니 캐스팅해주기
		if (userId == null) {
			// 비로그인이면 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		} 
			// DB글목록 조회
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		model.addAttribute("postList", postList);
		
		model.addAttribute("viewName", "post/postList");
		return "template/layout";
	}
	
	@GetMapping("/post-create-view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/postCreate");
		return "template/layout";
	}
	
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			Model model,
			HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		// DB조회 - postId + userId (로그인된 사람의 글이 맞는지)
		Post post = postBO.getPostByPostIdUserId(postId, userId);
		
		model.addAttribute("post", post);
		model.addAttribute("viewName", "post/postDetail");
		return "template/layout";
	}
}
