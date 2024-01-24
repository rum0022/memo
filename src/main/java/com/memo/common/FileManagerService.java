package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // spring bean  - 오토와이어드가 가능하다는 뜻
public class FileManagerService {

	// 실제 업로드 된 이미지가 저장될 경로 (서버주소) 
	public static final String FILE_UPLOAD_PATH = "D:\\choiareum\\6_spring_project\\MEMO\\memo_workspace\\images/";   // 마지막 슬래시 꼭붙인다.,상수는 대문자하는 특징있음
	// 집용 : public static final String FILE_UPLOAD_PATH = "D:\\choiareum\\6_spring_project\\MEMO\\memo_workspace\\images";
	
	//input : file원본,userLoginId(폴더명)    output : 이미지경로
	public String saveFile(String loginId, MultipartFile file) {
		// 폴더(디렉토리) 생성
		// 예시: aaaa_18323435/sun.png  아이디_밀리세컨초/확장자명
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName;  //D:\\choiareum\\6_spring_project\\MEMO\\memo_workspace\\images/aaaa_18323435/
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			//폴더 생성 실패시 이미지 경로는 null로 리턴
			return null;
		}
		
		// 파일 업로드 : byte 단위로 업로드
		try {
			byte[] bytes = file.getBytes();
			// ★★★★★★★★★ 한글 이름 이미지는 올릴 수 없으므로 나중에 영문자로 바꿔서 올리기 
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename());
			Files.write(path, bytes); //실제 파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null;  // 이미지 업로드 실패시 null리턴
		}
		
		// 파일업로드가 성공했으면 웹 이미지 url path를 리턴
		// 주소는 이렇게 될 것이다. (예언)
		// /images/aaaa_18323435/sun.png -> 이렇게 치고들어오면 웹주소로 그림을 볼 수 있음.
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	//input: imagePath      output:X
	public void deleteFile(String imagePath) { // /images/aaaa_1705483558718/winter-8425500_640.jpg 이런값이 온다
		// FILE_UPLOAD_PATH = "D:\\choiareum\\6_spring_project\\MEMO\\memo_workspace\\images/aaaa_1705483558718/winter-8425500_640.jpg
		// 주소에 겹치는 /image/ 지운다.   
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		// 삭제할 이미지가 존재하는가?
		if (Files.exists(path)) {
			// 이미지 삭제
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[파일매니저 삭제] 이미지 삭제 실패. path:{}", path.toString());
				return;
			}
			
			// 폴더(디렉토리)삭제
			path = path.getParent();
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[파일매니저 삭제] 폴더 삭제 실패. path:{}", path.toString());
				} // 마지막 단계라 리턴안해도 여기서 끝날것임
			}
		}
	}
}

