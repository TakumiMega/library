package com.example.demo;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import Bean.EmployeeForm;

@Controller
public class EmployeeController {
	

	@Autowired
	HttpSession session;

	@Autowired
	EmployeeRepository employeeRepository;
	
	
	
	
	//社員登録処理
		@RequestMapping(value="/library/addEmployee", method=RequestMethod.POST)
		public ModelAndView addEmployee(
				@ModelAttribute EmployeeForm employeeForm,
				ModelAndView mv
				) {
			
			//今日の日付と登録する社員IDを取得
			Date insertDate = new Date();
			Date updateDate = new Date();
			int insertEmployeeId = (int) session.getAttribute("employeeId");
			int updateEmployeeId = (int) session.getAttribute("employeeId");
			
			
			//名前51文字以上エラー
			if (employeeForm.getEmployeeName().length() > 50) {
				mv.addObject("message", "名前は50文字以下にしてください");
				mv.addObject("employeeForm",employeeForm);
				mv.setViewName("addEmployee");
				return mv;
			}
			
			//パスワード大文字小文字含む英数字8文字以上
			if(!(passcheck(employeeForm.getEmployeePass()))){
				mv.addObject("message", "パスワードには大文字小文字含む英数字を使用し、8文字以上20文字以下にしてください");
				mv.addObject("employeeForm",employeeForm);
				mv.setViewName("addEmployee");
				return mv;
			}
			
			//パスワード再確認と一致しない
			if(!(employeeForm.getEmployeePass().equals(employeeForm.getEmployeeRePass()))) {
				mv.addObject("message", "パスワードが一致しません");
				mv.addObject("employeeForm", employeeForm);
				mv.setViewName("addEmployee");
				return mv;
			}
			
			//既に登録されている名前とパスワードの確認
			Employee sameEmployee = employeeRepository.findByEmployeeNameAndEmployeePass(employeeForm.getEmployeeName(), employeeForm.getEmployeePass());
			if(sameEmployee != null) {
				mv.addObject("message", "違うパスワードを入力してください");
				mv.addObject("employeeForm",employeeForm);
				mv.setViewName("addEmployee");
				return mv;
			}
			
			//社員登録
			Employee employee = new Employee(employeeForm.getEmployeeName(), employeeForm.getEmployeePass(), insertDate, insertEmployeeId, updateDate, updateEmployeeId, employeeForm.getPositionId());
			employeeRepository.saveAndFlush(employee);
			mv.addObject("message", "登録が完了しました");
			mv.addObject("employeeForm",employeeForm);
			mv.setViewName("addEmployee");
			return mv;
			
		}
		
		public static boolean passcheck(String pass) {
			boolean result = true;
			String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z\\-]{8,20}$" ;
			Pattern pattern = Pattern.compile(check);
			Matcher m1 = pattern.matcher(pass); // パターンと検査対象文字列の照合
			   result = m1.matches(); // 照合結果をtrueかfalseで取得
			   return result;
		}
}
