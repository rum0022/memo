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
	public String postListView(
			@RequestParam(value = "prevId", required = false) Integer prevIdParam, // null 가능
			@RequestParam(value = "nextId", required = false) Integer nextIdParam,
			Model model, HttpSession session) {
		// 로그인이 되있어야하므로 권한검사도하기 (로그인여부조회)
		Integer userId = (Integer)session.getAttribute("userId");  // object로 뺄수 없으니 캐스팅해주기
		if (userId == null) {
			// 비로그인이면 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		} 
			// DB글목록 조회
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int nextId = 0;
		int prevId = 0;
		if (postList.isEmpty() == false) {
			// 포스트리스트가 비어있을때 [] 오류를 방지하기 위함
			prevId = postList.get(0).getId(); // 리스트의 첫번째 글번호
			nextId = postList.get(postList.size() - 1).getId(); // 리스트의 마지막 글번호
			
			// 이전 방향의 끝인가?
			// prevId와 post 테이블의 가장 큰 id값과 같으면 이전 페이지 없음.
			if (postBO.isPrevLastPageByUserId(userId, prevId)) {
				prevId = 0;
			}
			
			// 다음 방향의 끝인가?
			// nextId와 post 테이블의 가장작은 id값과 같으면 다음 페이지 없음.
			if (postBO.isNextLastPageByUserId(userId, nextId)) {
				nextId = 0;
			}
		}
		
		model.addAttribute("nextId", nextId);
		model.addAttribute("prevId", prevId);
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
