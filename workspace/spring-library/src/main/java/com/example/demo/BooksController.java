package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.BooksBean;
import Bean.UpdateForm;
import DAO.BooksDAO;
import DAO.DAOException;

@Controller
public class BooksController {
	@Autowired
	HttpSession session;
	int classificationId = 0;

	@Autowired
	ClassificationRepository classificationRepository;

	@RequestMapping(value = "/library/serchBooks")
	public ModelAndView showBooksName(
			@RequestParam("booksName") String booksName,
			@RequestParam("booksAuthor") String booksAuthor,
			@RequestParam("classificationId") int classificationId,
			@ModelAttribute UpdateForm updateform,
			ModelAndView mv) throws DAOException {
		List<Classification> classificationList = classificationRepository.findAll();
		UpdateForm form = new UpdateForm();
		BooksDAO dao = new BooksDAO();

		if (booksName.length() > 100) {
			mv.addObject("message", "図書名は100文字までしか入力できません");
			mv.setViewName("books");
			return mv;
		}

		//著者名文字チェック 51字以上入力の場合エラー
		if (booksAuthor.length() > 50) {
			mv.addObject("message", "著者名は50文字までしか入力できません");
			mv.setViewName("books");
			return mv;
		}
		List<BooksBean> booksList = dao.searchBooksInfo(booksName);
		session.setAttribute("booksList", booksList);
		mv.addObject("booksName", booksName);
		mv.addObject("booksList", booksList);
		//名前入力○
		if (!(booksName.length() == 0)) {
			booksList = dao.searchBooksName(booksName);
			mv.addObject("booksName", booksName);
			mv.addObject("booksList", booksList);
			//名前入力○著者入力○
			if (!(booksName.length() == 0) && !(booksAuthor.length() == 0)) {
				booksList = dao.searchBooksNameAndAuthor(booksName, booksAuthor);
				mv.addObject("booksName", booksName);
				mv.addObject("booksList", booksList);
			} else if (!(booksName.length() == 0) && !(booksAuthor.length() == 0) && !(classificationId == 0)) {
				booksList = dao.searchBooksNameAndAuthorAndclassificationId(booksName, booksAuthor, classificationId);
				mv.addObject("booksName", booksName);
				mv.addObject("booksList", booksList);
			}
		}
		//著者入力○
		else if (!(booksAuthor.length() == 0)) {
			booksList = dao.searchBooksAuthor(booksAuthor);
			mv.addObject("booksAuthor", booksAuthor);
			mv.addObject("booksList", booksList);
		}
		//分類入力○
		else if (!(classificationId == 0)) {
			booksList = dao.searchBooksclassificationId(classificationId);
			mv.addObject("classificationId", classificationId);
			mv.addObject("booksList", booksList);
			//名前＋分類
			if (!(booksName.length() == 0) && !(classificationId == 0)) {
				booksList = dao.searchBooksNameAndclassificationId(booksName, classificationId);
				mv.addObject("booksName", booksName);
				mv.addObject("booksList", booksList);
			}
			//著者名＋分類
			if (!(booksAuthor.length() == 0) && !(classificationId == 0)) {
				booksList = dao.searchBooksAuthorAndclassificationId(booksAuthor, classificationId);
				mv.addObject("booksAuthor", booksAuthor);
				mv.addObject("booksList", booksList);
			}
		}
		//表示させるHTMLをセット
		form.setClassificationList(classificationList);
		mv.setViewName("updateBooks");
		mv.addObject("updateForm", form);
		mv.setViewName("books");
		return mv;
	}

}
