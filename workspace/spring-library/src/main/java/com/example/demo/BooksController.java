package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.BooksBean;
import DAO.BooksDAO;
import DAO.DAOException;

@Controller
public class BooksController {
	@RequestMapping("/books")
	public String loginPage() {
		return "books";
	}
	@RequestMapping(value = "/library/serchBooks")
	public ModelAndView showBooks(
			@RequestParam("booksName") String booksName,
			ModelAndView mv) throws DAOException{

		BooksDAO dao = new BooksDAO();
		List<BooksBean> booksList = dao.searchBooks(booksName);
		mv.addObject("booksName", booksName);
		mv.addObject("booksList", booksList);

		//表示させるHTMLをセット
		mv.setViewName("books");

		return mv;
	}



}
