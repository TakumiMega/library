package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.BooksBean;
import DAO.BooksDAO;
import DAO.DAOException;

@Controller
public class LendController {

	@RequestMapping("/")
	public String loginPage() {
		return "lending";
	}

	//検索サブウィンド表示
	@RequestMapping(value = "/library/lendingSub")
	public ModelAndView showLendingSub(
			@RequestParam("booksName") String booksName,
			ModelAndView mv) throws DAOException{

		BooksDAO dao = new BooksDAO();
		List<BooksBean> booksList = dao.searchBooks(booksName);
		mv.addObject("booksName", booksName);
		mv.addObject("booksList", booksList);

		//表示させるHTMLをセット
		mv.setViewName("lendingSub");

		return mv;
	}

	@RequestMapping(value = "/library/lending/{booksId}")
	public ModelAndView deleteHouseHold(
			@PathVariable("booksId") int booksId,
			ModelAndView mv) throws NumberFormatException, DAOException {

		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}
}
