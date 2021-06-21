package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	@RequestMapping(value="/library/addUsers", method=RequestMethod.POST)
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
		if(!(usersForm.getUsersPhone().equals("")) && !(isNumber(usersForm.getUsersPhone()))) {
			mv.addObject("message", "電話番号が正しくありません");
			mv.addObject("usersForm",usersForm);
			mv.setViewName("addUsers");
			return mv;
		}


		//同じ利用者名と生年月日が既に登録されている場合
		Users sameUsers = usersRepository.findByUsersNameAndUsersBirthday(usersForm.getUsersName(), usersBirthday);
		if(sameUsers != null) {
			mv.addObject("message", "既に同じ名前と生年月日のユーザが存在しますが、登録しますか");
			mv.addObject("usersForm",usersForm);
			mv.setViewName("confirmUsers");
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


	//確認後の利用者登録処理
	@RequestMapping(value="/library/ConfirmUsers", method=RequestMethod.POST)
	public ModelAndView ConfirmUsers(
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

		//データを登録
		Users users = new Users(usersForm.getUsersName(), usersForm.getUsersAddress(), usersBirthday, usersForm.getUsersPhone(), usersForm.getUsersEmail(), insertDate, insertEmployeeId, updateDate, updateEmployeeId);
		usersRepository.saveAndFlush(users);
		mv.addObject("message", "登録が完了しました");
		mv.addObject("usersForm",usersForm);
		mv.setViewName("addUsers");

		return mv;
	}


	//利用者IDの検索
	@RequestMapping("/library/searchUsers")
	public ModelAndView searchUsers(
			@RequestParam("usersId") String usersId,
			ModelAndView mv
			) {

		//検索フォームに何も入力されずに検索ボタンを押下された場合、全件表示
		if(usersId.equals("")) {
			//ユーザの一覧を取得
			List<Users> usersList = usersRepository.findAll();
			mv.addObject("usersList",usersList);
			mv.setViewName("usersList");
			return mv;
		}
		
		//利用者IDの数値チェック
		if(!(isNumber(usersId))) {
			mv.addObject("message", "正しいIDを入力してください");
			mv.setViewName("usersList");
			return mv;
		}

		//IDが8桁で入力されていなかった場合
		if(usersId.length() != 8) {
			mv.addObject("message", "正しいIDを入力してください");
			mv.setViewName("usersList");
			return mv;
		}

		//入力されたIDの0を除去
		Pattern p = Pattern.compile("^0+([0-9]+.*)");
		Matcher m = p.matcher(usersId);
		String outUsersId = null;
		if (m.matches()) {
			outUsersId = m.group(1);
		}
		int serchUsersId  = Integer.parseInt(outUsersId);

		//検索対象の情報を取得
		Users usersList = usersRepository.findByUsersId(serchUsersId);

		//
		if(usersList == null) {
			mv.addObject("message", "その会員番号の利用者は存在しません");
			mv.setViewName("usersList");
			return mv;
		}

		mv.addObject("usersId",usersId);
		mv.addObject("usersList",usersList);
		mv.setViewName("usersList");
		return mv;
	}


	//利用者更新画面に遷移
	@RequestMapping("/library/updateUsersPage")
	public ModelAndView updateUsersPage(
			@RequestParam("usersId") int usersId,
			@ModelAttribute UsersForm usersForm,
			ModelAndView mv
			) {

		Users users = usersRepository.findByUsersId(usersId);
		String usersBirthday = dateFormat.format(users.getUsersBirthday());

		usersForm = new UsersForm(users.getUsersName(), usersBirthday, users.getUsersAddress(), users.getUsersPhone(), users.getUsersEmail());

		mv.addObject("usersId",usersId);
		mv.addObject("usersForm",usersForm);
		mv.setViewName("updateUsers");
		return mv;
	}


	//利用者更新処理
	@RequestMapping(value="/library/updateUsers", method=RequestMethod.POST)
	public ModelAndView updateUsers(
			@RequestParam("usersId") int usersId,
			@ModelAttribute UsersForm usersForm,
			ModelAndView mv
			) throws ParseException {

		//入力された利用者の誕生日をString型から Data型に変換
		Date usersBirthday = dateFormat.parse(usersForm.getUsersBirthday());
	
		//今日の日付と登録する社員IDを取得
		Date updateDate = new Date();
		int updateEmployeeId = (int) session.getAttribute("employeeId");
	
		//生年月日エラー
		Date today = new Date();	//今日の日付を取得
		if(usersBirthday.after(today)) {	//今日の日付と取得した日付を比較
			mv.addObject("message", "生年月日が正しくありません");
			mv.addObject("usersId",usersId);
			mv.addObject("usersForm",usersForm);
			mv.setViewName("updateUsers");
			return mv;
		}
	
		//電話番号の数値チェック
		if(!(usersForm.getUsersPhone().equals("")) && !(isNumber(usersForm.getUsersPhone()))) {
			mv.addObject("message", "電話番号が正しくありません");
			mv.addObject("usersId",usersId);
			mv.addObject("usersForm",usersForm);
			mv.setViewName("updateUsers");
			return mv;
		}
	
	
	
		Users insertEmployee = usersRepository.findByUsersId(usersId);
	
		Users updateUsers = new Users(usersId, usersForm.getUsersName(), usersForm.getUsersAddress(), usersBirthday, usersForm.getUsersPhone(), usersForm.getUsersEmail(), insertEmployee.getInsertDate(), insertEmployee.getInsertEmployeeId(), updateDate, updateEmployeeId);
		usersRepository.saveAndFlush(updateUsers);
		mv.addObject("message", "登録が完了しました");
		mv.addObject("usersId",usersId);
		mv.addObject("usersForm",usersForm);
		mv.setViewName("updateUsers");
	
		return mv;
	}

	public boolean isNumber(String number) {
		try {
			Long.parseLong(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}



}
