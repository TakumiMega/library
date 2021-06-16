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
	int classificationId=0;
	@RequestMapping(value = "/library/serchBooks")
	public ModelAndView showBooksName(
			@RequestParam("booksName") String booksName,
			@RequestParam("booksAuthor") String booksAuthor,
			@RequestParam("classificationId") int classificationId,
			ModelAndView mv) throws DAOException {
		BooksDAO dao = new BooksDAO();
		//名前入力○
		if (!(booksName.length()==0)) {
			List<BooksBean> booksList = dao.searchBooksName(booksName);
			mv.addObject("booksName", booksName);
			mv.addObject("booksList", booksList);
			//名前入力○著者入力○
			if(!(booksName.length()==0)&&!(booksAuthor.length()==0)) {
				booksList = dao.searchBooksNameAndAuthor(booksName,booksAuthor);
				mv.addObject("booksName", booksName);
				mv.addObject("booksList", booksList);
			}
			else if(!(booksName.length()==0)&&!(booksAuthor.length()==0)&&!(classificationId==0)) {
				booksList = dao.searchBooksNameAndAuthorAndclassificationId(booksName,booksAuthor,classificationId);
				mv.addObject("booksName", booksName);
				mv.addObject("booksList", booksList);
			}
		}
		//著者入力○
		else if(!(booksAuthor.length()==0)) {
			List<BooksBean> booksList = dao.searchBooksAuthor(booksAuthor);
			mv.addObject("booksAuthor", booksAuthor);
			mv.addObject("booksList", booksList);
		}
		else if(!(classificationId==0)) {
			List<BooksBean> booksList = dao.searchBooksclassificationId(classificationId);
			mv.addObject("classificationId", classificationId);
			mv.addObject("booksList", booksList);
		}
		//表示させるHTMLをセット
		mv.setViewName("books");
		return mv;
	}

}
