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
	public String booksinfo() {
		return "books";
	}
	@RequestMapping(value = "/library/serchBooks")
	public ModelAndView showBooksName(
			@RequestParam("booksName") String booksName,
			ModelAndView mv) throws DAOException{

		BooksDAO dao = new BooksDAO();
		List<BooksBean> booksList = dao.searchBooksName(booksName);
		mv.addObject("booksName", booksName);
		mv.addObject("booksList", booksList);

		//表示させるHTMLをセット
		mv.setViewName("books");

		return mv;
	}
	public ModelAndView showBooksAuthor(
			@RequestParam("booksAuthor") String booksAuthor,
			ModelAndView mv) throws DAOException{

		BooksDAO dao = new BooksDAO();
		List<BooksBean> booksList = dao.searchBooksAuthor(booksAuthor);
		mv.addObject("booksAuthor", booksAuthor);
		mv.addObject("booksList", booksList);

		//表示させるHTMLをセット
		mv.setViewName("books");

		return mv;
	}



}
