package com.memo.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.memo.post.domain.Post;

@Mapper
public interface PostMapper {
	//input:x      output:List<post>
	public List<Post> selectPostListByUserId(int userId);
}
