package com.cafe24.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping({"", "/main", "/main/index"})
	public String index() {
		System.out.println("main 페이지");
		return "main/index";
	}
}
