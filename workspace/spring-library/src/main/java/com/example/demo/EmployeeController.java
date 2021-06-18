package com.example.demo;

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

import Bean.EmployeeForm;

@Controller
public class EmployeeController {
	

	@Autowired
	HttpSession session;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	
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
		
		//役職選択に使用する情報をリストに格納する
		List<Position> positionList = positionRepository.findAll();			
		
		//パスワード大文字小文字含む英数字8文字以上
		if(!(passcheck(employeeForm.getEmployeePass()))){
			mv.addObject("message", "パスワードには大文字小文字含む英数字を使用し、8文字以上30文字以下にしてください");
			mv.addObject("employeeForm",employeeForm);
			mv.addObject("positionList",positionList);
			mv.setViewName("addEmployee");
			return mv;
		}
		
		//パスワード再確認と一致しない
		if(!(employeeForm.getEmployeePass().equals(employeeForm.getEmployeeRePass()))) {
			mv.addObject("message", "パスワードが一致しません");
			mv.addObject("employeeForm", employeeForm);
			mv.addObject("positionList",positionList);
			mv.setViewName("addEmployee");
			return mv;
		}
		
		//既に登録されている名前とパスワードの確認
		Employee sameEmployee = employeeRepository.findByEmployeeNameAndEmployeePass(employeeForm.getEmployeeName(), employeeForm.getEmployeePass());
		if(sameEmployee != null) {
			mv.addObject("message", "違うパスワードを入力してください");
			mv.addObject("employeeForm",employeeForm);
			mv.addObject("positionList",positionList);
			mv.setViewName("addEmployee");
			return mv;
		}
		
		//社員登録
		Employee employee = new Employee(employeeForm.getEmployeeName(), employeeForm.getEmployeePass(), insertDate, insertEmployeeId, updateDate, updateEmployeeId, employeeForm.getPositionId());
		employeeRepository.saveAndFlush(employee);
		mv.addObject("message", "登録が完了しました");
		mv.addObject("employeeForm",employeeForm);
		mv.addObject("positionList",positionList);
		mv.setViewName("addEmployee");
		return mv;
		
	}
		
	//社員検索処理
	@RequestMapping("/library/searchEmployee")
	public ModelAndView searchUsers(
			@RequestParam("employeeId") String employeeId,
			ModelAndView mv
			) {

		//検索フォームに何も入力されずに検索ボタンを押下された場合、全件表示
		if(employeeId.equals("")) {
			//ユーザの一覧を取得
			List<Employee> employeeList = employeeRepository.findAll();
			mv.addObject("employeeList",employeeList);
			mv.setViewName("employeeList");
			return mv;
		}
		
		//利用者IDの数値チェック
		if(!(isNumber(employeeId))) {
			mv.addObject("message", "正しいIDを入力してください");
			mv.setViewName("employeeList");
			return mv;
		}

		//IDが8桁で入力されていなかった場合
		if(employeeId.length() != 8) {
			mv.addObject("message", "正しいIDを入力してください");
			mv.setViewName("employeeList");
			return mv;
		}

		//入力されたIDの0を除去
		Pattern p = Pattern.compile("^0+([0-9]+.*)");
		Matcher m = p.matcher(employeeId);
		String outEmployeeId = null;
		if (m.matches()) {
			outEmployeeId = m.group(1);
		}
		int serchemployeeId  = Integer.parseInt(outEmployeeId);

		//検索対象の情報を取得
		Employee employeeList = employeeRepository.findByEmployeeId(serchemployeeId);

		//
		if(employeeList == null) {
			mv.addObject("message", "その会員番号の利用者は存在しません");
			mv.setViewName("employeeList");
			return mv;
		}

		mv.addObject("employeeId",employeeId);
		mv.addObject("employeeList",employeeList);
		mv.setViewName("employeeList");
		return mv;
	}
	
		
	public static boolean passcheck(String pass) {
		boolean result = true;
		String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z\\-]{8,30}$" ;
		Pattern pattern = Pattern.compile(check);
		Matcher m1 = pattern.matcher(pass); // パターンと検査対象文字列の照合
		   result = m1.matches(); // 照合結果をtrueかfalseで取得
		   return result;
	}
	
	public boolean isNumber(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
