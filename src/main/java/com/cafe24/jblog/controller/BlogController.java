package com.cafe24.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("")
	public String blogMain(@PathVariable("id") String id, Model model){
		if(blogService.getBlogInfo(id) == null) {
			return "/error/404";
		}
		
		model.addAttribute("blogId", id);
		return "/blog/blog-main";
	}
	
	@GetMapping("admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model){
		
		model.addAttribute("blogInfo", blogService.getBlogInfo(id));
		model.addAttribute("blogId", id);
		return "/blog/blog-admin-basic";
	}
	
	@PostMapping("admin/basic")
	public String adminBasicPost(@PathVariable("id") String id, @ModelAttribute BlogVo vo){
		
		return "/blog/blog-admin-basic";
	}
	
	@GetMapping("admin/category")
	public String adminCategory(@PathVariable("id") String id, Model model){
		
		model.addAttribute("blogId", id);
		return "/blog/blog-admin-category";
	}
	
	@GetMapping("admin/write")
	public String adminWrite(@PathVariable("id") String id, Model model){
		
		model.addAttribute("blogId", id);
		return "/blog/blog-admin-write";
	}
}
