package com.cafe24.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insertCategory(CategoryVo vo) {
		
		int count = sqlSession.insert("category.insertCategory", vo);
		
		return 1 == count;
	}
	
}
