package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Bean.ClassificationBean;
import Bean.EmployeeForm;
import Bean.LendingBean;
import Bean.LendingListForm;
import Bean.UsersForm;
import DAO.ClassificationDAO;
import DAO.DAOException;
import DAO.LendingDAO;

@Controller
public class MainController {
	final Calendar calendar = Calendar.getInstance();
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	PositionRepository positionRepository;

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
			) throws DAOException{
		ClassificationDAO classdao = new ClassificationDAO();
		LendingDAO lendao = new LendingDAO();
		LendingListForm form = new LendingListForm();
		List<ClassificationBean> classificationList = classdao.searchClassification();
		form.setClassificationList(classificationList);

		List<LendingBean> lendingList = lendao.searchFirstLendingList();
		mv.addObject("lendingList",lendingList);
		mv.addObject("form",form);
		mv.setViewName("lendingList");
		return mv;
	}

	//返却超過一覧画面に遷移
	@RequestMapping("/library/returnOver")
	public ModelAndView returnOver(
			ModelAndView mv
			) throws DAOException{
		LendingDAO lendao = new LendingDAO();
		List<LendingBean> lendingList = lendao.searchFirstLendingOverList();
		mv.addObject("lendingList",lendingList);
		mv.setViewName("returnOver");
		return mv;
	}

	//利用者一覧画面に遷移
	@RequestMapping("/library/usersList")
	public ModelAndView usersList(
			ModelAndView mv
			){

		//ユーザの一覧を取得
		List<Users> usersList = usersRepository.findAll();
		mv.addObject("usersList",usersList);
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
			) throws DAOException{
		
		// モデルのDAOを生成
		EmployeeDAO employeeDAO = new EmployeeDAO();
		List<EmployeeListBean> employeeList = employeeDAO.findAll();
		mv.addObject("employeeList",employeeList);
		mv.setViewName("employeeList");
		return mv;
	}

	//社員登録画面に遷移
	@RequestMapping("/library/addEmployeePage")
	public ModelAndView addEmployeePage(
			EmployeeForm employeeForm,
			ModelAndView mv
			){

		//役職選択に使用する情報をリストに格納する
		List<Position> positionList = positionRepository.findAll();

		mv.addObject("positionList",positionList);
		mv.addObject("employeeForm",employeeForm);
		mv.setViewName("addEmployee");
		return mv;
	}

	
}
