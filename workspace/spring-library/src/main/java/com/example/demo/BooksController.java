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
import Bean.ClassificationBean;
import Bean.UpdateForm;
import DAO.BooksDAO;
import DAO.ClassificationDAO;
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
		ClassificationDAO classdao = new ClassificationDAO();
		List<ClassificationBean> classificationList = classdao.searchClassification();
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
		if (!(classificationId == 0)) {
			booksList = dao.searchBooksNameAndAuthorAndclassificationId(booksName, booksAuthor, classificationId);
		}else {
			booksList = dao.searchBooksNameAndAuthor(booksName, booksAuthor);
		}

		mv.addObject("booksName", booksName);
		mv.addObject("booksList", booksList);
		//表示させるHTMLをセット
		form.setClassificationId(classificationId);
		form.setClassificationshowList(classificationList);
		mv.setViewName("updateBooks");
		mv.addObject("updateForm", form);
		mv.setViewName("books");
		return mv;
	}

}
