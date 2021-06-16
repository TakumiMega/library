package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UsersRepository usersRepository;
	
	//利用者登録処理
	@RequestMapping("/library/addUsers")
	public ModelAndView addUsers(
			@ModelAttribute UsersForm usersForm,
			ModelAndView mv
			) throws ParseException{
		
		//入力された利用者の誕生日をString型から Data型に変換		
		Date usersBirthday = dateFormat.parse(usersForm.getUsersBirthday());
		
		//今日の日付と登録する社員IDを取得
		Date insertDate = new Date();
		Date updateDate = new Date();
		int insertEmployeeId = (int) session.getAttribute("employeeId");
		int updateEmployeeId = (int) session.getAttribute("employeeId");
		
		
		//生年月日エラー
		Date today = new Date();	//今日の日付を取得
		if(usersBirthday.after(today)) {	//今日の日付と取得した日付を比較
			mv.addObject("message", "生年月日が正しくありません");
			mv.addObject("usersForm",usersForm);
			mv.setViewName("addUsers");
			return mv;
		}		
		
		//電話番号の数値チェック
		if(!(isNumber(usersForm.getUsersPhone()))) {
			mv.addObject("message", "電話番号が正しくありません");
			mv.addObject("usersForm",usersForm);
			mv.setViewName("addUsers");
			return mv;
		}
		
		//名前51文字以上エラー
		if (usersForm.getUsersName().length() > 50) {
			mv.addObject("message", "名前は50文字以下にしてください");
			mv.addObject("usersForm",usersForm);
			mv.setViewName("addUsers");
			return mv;
		}
		//住所101文字以上エラー
		if (usersForm.getUsersAddress().length() > 100) {
			mv.addObject("message", "住所は100文字までしか入力できません");
			mv.addObject("usersForm",usersForm);
			mv.setViewName("addUsers");
			return mv;
		}
		//電話番号21文字以上エラー
		if (usersForm.getUsersPhone().length() > 20) {
			mv.addObject("message", "正しい電話番号を入力してください");
			mv.addObject("usersForm",usersForm);
			mv.setViewName("addUsers");
			return mv;
		}
		
		//データを登録
		Users users = new Users(usersForm.getUsersName(), usersForm.getUsersAddress(), usersBirthday, usersForm.getUsersPhone(), usersForm.getUsersEmail(), insertDate, insertEmployeeId, updateDate, updateEmployeeId);
		usersRepository.saveAndFlush(users);
		mv.addObject("message", "登録が完了しました");
		mv.addObject("usersForm",usersForm);
		mv.setViewName("addUsers");
		return mv;
	}
	
	
	
	public boolean isNumber(String booksStock) {
		try {
			Integer.parseInt(booksStock);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	
}
