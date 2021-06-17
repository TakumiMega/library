package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.ClassificationBean;
import Bean.LendingBean;
import Bean.LendingListForm;
import DAO.ClassificationDAO;
import DAO.DAOException;
import DAO.LendingDAO;

@Controller
public class LendingListController {

	@RequestMapping(value = "/library/lendingList/search")
	public ModelAndView deleteLending(
			@RequestParam("booksName") String booksName,
			@RequestParam("booksAuthor") String booksAuthor,
			@RequestParam("classificationId") String classificationId,
			ModelAndView mv) throws NumberFormatException, DAOException {
		LendingListForm form = new LendingListForm();
		ClassificationDAO classdao = new ClassificationDAO();
		LendingDAO lendao = new LendingDAO();
		if (classificationId.equals("0")) {
			List<LendingBean> lendingList = lendao.searchLendingListBooksNameBooksAuthorAll(booksName,booksAuthor);
			mv.addObject("lendingList", lendingList);

		} else {
			List<LendingBean> lendingList = lendao.searchLendingListBooksNameBooksAuthorClass(booksName,booksAuthor,Integer.parseInt(classificationId));
			mv.addObject("lendingList", lendingList);
		}
		List<ClassificationBean> classificationList = classdao.searchClassification();
		form.setClassificationList(classificationList);
		form.setBooksName(booksName);
		form.setBooksAuthor(booksAuthor);
		form.setClassificateId(Integer.parseInt(classificationId));
		mv.addObject("form",form);

		//表示させるHTMLをセット
		mv.setViewName("lendingList");

		return mv;
	}

}
