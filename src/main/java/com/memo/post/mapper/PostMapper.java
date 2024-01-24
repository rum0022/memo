package com.memo.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.post.domain.Post;

@Mapper
public interface PostMapper {
	//input:x      output:List<post>
	public List<Post> selectPostListByUserId(int userId);
	
	public void insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject, 
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	public Post selectPostByPostIdUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public void updatePostByPostId(
			@Param("postId") int postId,
			@Param("subject") String subject, 
			@Param("content") String content,
			@Param("imagePath") String imagePath);
}
