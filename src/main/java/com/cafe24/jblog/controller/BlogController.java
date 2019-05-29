package com.cafe24.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("")
	public String blogMain(@PathVariable("id") String id, BlogVo vo, Model model){
		
		vo = blogService.getBlogInfo(id);
		
		if( vo == null) {
			return "/error/404";
		}
		
		model.addAttribute("blogInfo", vo);
		model.addAttribute("blogId", id);
		return "/blog/blog-main";
	}
	
	
	////////////////////////////////////////////////// 관리자 기본설정 페이지
	
	@GetMapping("admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model){
		
		model.addAttribute("blogInfo", blogService.getBlogInfo(id));
		model.addAttribute("blogId", id);
		return "/blog/blog-admin-basic";
	}
	
	@PostMapping("admin/basic")
	public String adminBasicPost(@ModelAttribute BlogVo vo){
		
		blogService.updateBlogInfo(vo);
		
		return "redirect:/" + vo.getId() + "/admin/basic";
	}
	
	////////////////////////////////////////////////// 관리자 카테고리 페이지
	
	@GetMapping("admin/category")
	public String adminCategory(@PathVariable("id") String id, Model model){
		
		model.addAttribute("blogId", id);
		
		return "/blog/blog-admin-category";
	}
	
	@ResponseBody
	@PostMapping("admin/insertCategory")
	public JSONResult insertCategory(@ModelAttribute CategoryVo vo) {
		
		return JSONResult.success(blogService.insertCategory(vo));
	}
	
	@ResponseBody
	@GetMapping("admin/getCategoryList")
	public JSONResult getCategoryList(@RequestParam("id") String id) {
		
		return JSONResult.success(blogService.getCategoryList(id));
	}
	
	@ResponseBody
	@GetMapping("admin/deleteCategory")
	public JSONResult deleteCategory(@RequestParam("no") Long no) {
		
		return JSONResult.success(blogService.deleteCategory(no));
	}
	
	
	////////////////////////////////////////////////// 관리자 글작성 페이지
	
	@GetMapping("admin/write")
	public String adminWrite(@PathVariable("id") String id, Model model){
		
		model.addAttribute("categoryList", blogService.getCategoryList(id));
		model.addAttribute("blogId", id);
		return "/blog/blog-admin-write";
	}
	
	@PostMapping("admin/writePost")
	public String adminWritePost(@PathVariable("id") String id, @ModelAttribute PostVo vo){
		
		blogService.insertPost(vo);
		
		return "redirect:/" + id + "/admin/write";
	}
}
