package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.BooksBean;
import DAO.BooksDAO;
import DAO.DAOException;

@Controller
public class LendController {

	List<BooksBean> lendingList = new ArrayList<>();

	@RequestMapping("/")
	public String loginPage() {
		return "lending";
	}

	@GetMapping(value = "/library/lendingSub")
	public ModelAndView openWinByGet(
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

	@GetMapping(value = "/library/lending/{booksId}")
	public ModelAndView deleteHouseHold(
			@PathVariable("booksId") int booksId,
			ModelAndView mv) throws NumberFormatException, DAOException {

		BooksDAO dao = new BooksDAO();
		lendingList.addAll(dao.searchBooksId(booksId));
		mv.addObject("booksList", lendingList);

		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}
}
