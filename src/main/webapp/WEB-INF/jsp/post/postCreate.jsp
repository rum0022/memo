<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글쓰기</h1>
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요">
		<textarea id="content" class="form-control" placeholder="내용을 입력하세요" rows="10"></textarea>
		<div class="d-flex justify-content-end my-3">
			<input type="file" id="file" accept=".jpg, .png, .gif, ,jpeg">
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
			<div>
				<button type="button" id="clearBtn" class="btn btn-secondary">모두지우기</button>
				<button type="button" id="saveBtn" class="btn btn-info">저장</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		//목록 버튼 클릭 => 목록 화면 이동 (앵커태그처럼 하단에 사이트 주소 안나타남)
		$("#postListBtn").on("click", function() {
			location.href = "/post/post-list-view";
		});
		// 모두 지우기 버튼 클릭
		$("#clearBtn").on("click", function() {
			//alert("모두지우기");
			$("#subject").val("");
			$("#content").val("");
		});
		
		// 글 저장 버튼
		$("#saveBtn").on("click", function() {
			//alert("저장");
			let subject = $("#subject").val().trim();
			let content = $("#content").val();
			let fileName = $("#file").val(); //C:\fakepath\girl-8435329_640.png
			//alert(fileName); 
		
			// 유효성 검사
			
			if (!subject) {
				alert("제목을 입력하세요");
				return;
			}
			if (!content) {
				alert("내용을 입력하세요");
				return;
			}
			
			// 파일이 업로드 된경우에만 확장자 체크 검사하고, 업로드 안됐으면 그냥 저장
			if (fileName) {
				// alert("파일이 있다.");
				//C:\fakepath\girl-8435329_640.png
				// 확장자만 뽑은 후 소문자로 변경해서 검사한다. 
				let extension = fileName.split(".").pop().toLowerCase();
				// alert(extension);
				
				if ($.inArray(extension, ['jpg', 'png', 'gif', 'jpeg']) == -1) { //확장자가 이미지가 아닐경우
					alert("이미지 파일만 업로드 할 수 있습니다.");
					$("#file").val(""); // 파일명을 비운다.
					return;
				}
			}
			
			// form 태그를 js에서 만든다.
			// 이미지를 업로드 할 때는 반드시 폼태그가 있어야한다.
			let formData = new FormData();
			formData.append("subject", subject);  // (폼의 네임속성, 값(변수)) 키는 네임속성과 같다. RequestParmeter명
			formData.append("content", content);
			formData.append("file", $("#file")[0].files[0]); //멀티로 올릴때는 다른태그
			
			// 폼자체를 파라미터로 넘길 수 있음.
			// AJAX
			$.ajax({
				//request
				type:"POST" // 이미지는 포스트
				, url:"/post/create"
				, data:formData
				, enctype:"mutipart/form-data" // 파일업로드를 위한 필수 설정(이미지를 올리기위한 설정)
				, processData:false // 파일 업로드를 위한 필수 설정
				, contentType:false // 파일 업로드를 위한 필수 설정
			
				//response
				, success:function(data) {
					if (data.code == 200) {
						// 글목록 화면으로 이동
						alert("메모가 저장되었습니다.");
						location.href = "/post/post-list-view";
					} else {
						alert("data.error_message");
					}
				}
				, error: function(request, status, error) {
					alert("글을 저장하는데 실패 했습니다.");
				}
			});
		});
	});
	
</script>