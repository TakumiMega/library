package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Bean.UsersForm;

@Controller
public class MainController {

	
	@Autowired
	HttpSession session;
	
	//貸出画面に遷移
	@RequestMapping("/library/lending")
	public ModelAndView lending(
			ModelAndView mv
			){	
		mv.setViewName("lending");
		return mv;
	}
	
	//返却画面に遷移
	@RequestMapping("/library/returnning")
	public ModelAndView returnning(
			ModelAndView mv
			){	
		mv.setViewName("return");
		return mv;
	}
	
	//図書一覧画面に遷移
	@RequestMapping("/library/booksList")
	public ModelAndView booksList(
			ModelAndView mv
			){	
		mv.setViewName("books");
		return mv;
	}
	
	//図書登録画面に遷移
	@RequestMapping("/library/addBooksPage")
	public ModelAndView addBooksPage(
			ModelAndView mv
			){	
		mv.setViewName("addBooks");
		return mv;
	}
	
	//貸出図書一覧画面に遷移
	@RequestMapping("/library/lendingList")
	public ModelAndView lendingList(
			ModelAndView mv
			){	
		mv.setViewName("lendingList");
		return mv;
	}
	
	//返却超過一覧画面に遷移
	@RequestMapping("/library/returnOver")
	public ModelAndView returnOver(
			ModelAndView mv
			){	
		mv.setViewName("returnOver.");
		return mv;
	}
	
	//利用者一覧画面に遷移
	@RequestMapping("/library/usersList")
	public ModelAndView usersList(
			ModelAndView mv
			){	
		mv.setViewName("usersList");
		return mv;
	}
	
	//利用者登録画面に遷移
	@RequestMapping("/library/addUsersPage")
	public ModelAndView addUsersPage(
			@ModelAttribute UsersForm usersForm, 
			ModelAndView mv
			){	
		mv.addObject("usersForm",usersForm);
		mv.setViewName("addUsers");
		return mv;
	}
	
	//社員一覧画面に遷移
	@RequestMapping("/library/employeeList")
	public ModelAndView employeeList(
			ModelAndView mv
			){	
		mv.setViewName("employeeList");
		return mv;
	}
	
	//社員登録画面に遷移
	@RequestMapping("/library/addEmployeePage")
	public ModelAndView addEmployeePage(
			ModelAndView mv
			){	
		mv.setViewName("addEmployee");
		return mv;
	}
	
	/*
	 * //お問い合わせ画面に遷移
	 * 
	 * @RequestMapping("/library/contact") public ModelAndView contact( ModelAndView
	 * mv ){ mv.setViewName(""); return mv; }
	 */
}
