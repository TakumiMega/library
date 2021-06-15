package com.example.demo;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Bean.UsersForm;

@Controller
public class UsersController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UsersRepository usersRepository;
	
	//利用者登録処理
	@RequestMapping("/library/addUsers")
	public ModelAndView addUsers(
			@ModelAttribute UsersForm usersForm,
			ModelAndView mv
			){
		
		Date insertDate = new Date();
		Date updateDate = new Date();
		
		int insertEmployeeId = (int) session.getAttribute("employeeId");
		int updateEmployeeId = (int) session.getAttribute("employeeId");
		
		//Users users = new Users(usersForm.getUsersName(), usersForm.getUsersAddress(), usersForm.getUsersBirthday(), usersForm.getUsersPhone(), usersForm.getUsersEmail(), insertDate, insertEmployeeId, updateDate, updateEmployeeId);
		//usersRepository.saveAndFlush(users);
		return mv;
	}
	
	
	
	
}
