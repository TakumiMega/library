package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class LoginController {

	private static final int receptionID  = 1;	//受付ID
	private static final int staffID  = 2;		//職員ID
	private static final int adminID  = 3;		//管理者ID

	@Autowired
	HttpSession session;

	@Autowired
	EmployeeRepository employeeRepository;

	/**
	 * トップ画面を表示
	 */
	@RequestMapping("/library")
	public ModelAndView top(
			ModelAndView mv
			){
		session.invalidate();
		mv.setViewName("login");
		return mv;
	}


	/**
	 * ログイン処理
	 */
	@RequestMapping("/library/main")
	public ModelAndView login(
			@RequestParam("employeeName") String employeeName,
			@RequestParam("employeePass") String employeePass,
			ModelAndView mv
			){

		// 社員名とパスワードを照合
		Employee employee = employeeRepository.findByEmployeeNameAndEmployeePass(employeeName, employeePass);
		// employeeがnullだった場合エラーメッセージを表示
		if (employee == null) {
			mv.addObject("message", "パスワードが違います。\n正しいパスワードを入力してください。");
			mv.addObject("employeeName", employeeName);
			mv.setViewName("login");
			return mv;
		}
		
		//sessionにログインした社員IDを格納
		session.setAttribute("employeeId", employee.getEmployeeId());

		//役職が受付だった場合、受付用のメイン画面を表示
		if(employee.getPositionId() == receptionID) {
			mv.setViewName("receptionMain");
		}

		//役職が職員だった場合、職員用のメイン画面を表示
		if(employee.getPositionId() == staffID) {
			mv.setViewName("staffMain");
		}

		//役職が管理者だった場合、管理者用のメイン画面を表示
		if(employee.getPositionId() == adminID) {
			mv.setViewName("adminMain");
		}

		return mv;
	}

}
