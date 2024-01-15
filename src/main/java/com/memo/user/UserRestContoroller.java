package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBo;
import com.memo.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestContoroller {

	@Autowired
	private UserBo userBo;
	
	/**
	 * 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id") // 겟, 포스트이면 리퀘스트 쓰기
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		//DB조회 - select
		UserEntity user = userBo.getUserEntityByLoginId(loginId);
		
		Map<String, Object> result = new HashMap<>();
		if (user != null) { // 중복
			result.put("code", 200);
			result.put("is-duplicated-id", true);
		} else { // 중복아님
			result.put("code", 200);
			result.put("is-duplicated-id", true);
		}
		return result;
	}
	
	/**
	 * 회원가입API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// 암호화(md5 알고리즘) - 암호 취약함 => password hashing 
		// static이라 뉴를 안해도 객체생성됨
		String hashedPassword = EncryptUtils.md5(password);
		
		// db insert
		Integer userId = userBo.addUser(loginId, hashedPassword, name, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (userId != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
	
	@PostMapping("sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) { // 세션
		// 비밀번호 해싱 - mb5
		String hashedPassword = EncryptUtils.md5(password);
		
		// db select (loginId, 해싱된 비번이 있는지 조회) => UserEntity 
		UserEntity user = userBo.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 응답값
		Map<String, Object> result = new HashMap<>(); //유저가 있을수도 없을 수도있음. 
		if (user != null) { // 로그인 성공
			// 로그인 처리
			// 로그인 정보를 세션에 담는다(사용자마다)
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("loginId", user.getLoginId());
			session.setAttribute("name", user.getName());
			
			result.put("code", 200);
			result.put("result", "성공");
		} else { // 그런사람 없는 상황
			//로그인 불가
			result.put("code", 300); // 권한이 없는건 300대
			result.put("error_message", "존재하지 않는 사용자 입니다.");
		}
		return result;
	}
}
