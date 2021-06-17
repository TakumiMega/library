package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UpdateController {
	@RequestMapping("/update")
	public String booksinfo() {
		return "update";
	}
}
