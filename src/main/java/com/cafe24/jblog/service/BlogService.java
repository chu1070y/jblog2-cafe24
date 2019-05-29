package com.cafe24.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	public BlogVo getBlogInfo(String id) {
		return blogDao.getBlogInfo(id);
	}

	public Boolean updateBlogInfo(BlogVo vo) {
		
		String url = fileUploadService.restore(vo.getMultipartFile());
		vo.setLogo(url);
		System.out.println(url);
		
		return blogDao.updateBlog(vo);
	}
	
	public List<CategoryVo> getCategoryList(String id){
		return categoryDao.getCategoryList(id);
	}
	
	public Boolean insertCategory(CategoryVo vo) {
		return categoryDao.insertCategory(vo);
	}
	
	public Boolean deleteCategory(Long no) {
		return categoryDao.deleteCategory(no);
	}
	
	public Boolean insertPost(PostVo vo) {
		return postDao.insertPost(vo);
	}

}
