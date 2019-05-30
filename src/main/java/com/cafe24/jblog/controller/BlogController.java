package com.cafe24.jblog.controller;

import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:^(?!assets|images).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
//////////////////////////////////////////////////블로그 메인 페이지
	
	@GetMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String blogMain(
			@PathVariable("id") String id,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo,
			PostVo postVo, BlogVo blogVo, Model model){
		
		blogVo = blogService.getBlogInfo(id);
		
		if(blogVo == null) {
			return "/error/404";
		}
		
		model.addAttribute("categorys", blogService.getCategoryList(id));
		model.addAttribute("posts", blogService.getPost());
		
		model.addAttribute("blogInfo", blogVo);
		return "/blog/blog-main";
	}
	
	
	////////////////////////////////////////////////// 관리자 기본설정 페이지
	
	@GetMapping("admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model){
		
		model.addAttribute("blogInfo", blogService.getBlogInfo(id));
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
		
		model.addAttribute("blogInfo", blogService.getBlogInfo(id));
		
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
	public JSONResult deleteCategory(@PathVariable("id") String id, @ModelAttribute CategoryVo vo) {
		vo.setBlogId(id);
		System.out.println(vo);
		return JSONResult.success(blogService.deleteCategory(vo));
	}
	
	
	////////////////////////////////////////////////// 관리자 글작성 페이지
	
	@GetMapping("admin/write")
	public String adminWrite(@PathVariable("id") String id, Model model){
		
		model.addAttribute("categoryList", blogService.getCategoryList(id));
		model.addAttribute("blogInfo", blogService.getBlogInfo(id));
		return "/blog/blog-admin-write";
	}
	
	@PostMapping("admin/writePost")
	public String adminWritePost(@PathVariable("id") String id, @ModelAttribute PostVo vo, RedirectAttributes redirect){
		
		if(vo.getCategoryNo() == null) {
			System.out.println("null");
			redirect.addFlashAttribute("result", "fail");
		} else {
			vo.setId(id);
			blogService.insertPost(vo);
			redirect.addFlashAttribute("result", "success");
		}
		
		return "redirect:/" + id + "/admin/write";
	}
}
