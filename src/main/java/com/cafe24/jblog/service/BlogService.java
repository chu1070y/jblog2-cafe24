package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.vo.BlogVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
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

}
