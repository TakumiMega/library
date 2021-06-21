package com.example.demo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.UpdateForm;

@Controller
public class UpdateController {
	@Autowired
	HttpSession session;

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	ClassificationRepository classificationRepository;

	@RequestMapping("/library/updateBooksPage/{booksId}")
	public ModelAndView updateBooks(
			ModelAndView mv,
			@PathVariable("booksId") int booksId) {
		UpdateForm form = new UpdateForm();
		List<Classification> classificationList = classificationRepository.findAll();
		Books books = booksRepository.findByBooksId(booksId);

		form.setBooksId(booksId);
		form.setBooksName(books.getBooksName());
		form.setBooksAuthor(books.getBooksAuthor());
		form.setClassificationId(books.getClassificationId());
		form.setBooksStock(String.valueOf(books.getBooksStock()));
		form.setBooksRemarks(books.getBooksRemarks());
		form.setBooksLend(books.getBooksLend());
		form.setClassificationList(classificationList);
		mv.addObject("updateForm", form);
		mv.setViewName("updateBooks");
		return mv;
	}

	@RequestMapping("/library/updateBooks/{booksId}")
	public ModelAndView updateBooksPage(@PathVariable("booksId") String booksId,
			@RequestParam(name = "booksName") String booksName,
			@RequestParam(name = "booksAuthor") String booksAuthor,
			@RequestParam(name = "booksStock") String booksStock,
			@RequestParam(name = "booksLend") String booksLend,
			@RequestParam(name = "booksRemarks") String booksRemarks,
			@RequestParam(name = "classificationId") int classificationId,
			@ModelAttribute UpdateForm updateform,
			ModelAndView mv) {
		List<Classification> classificationList = classificationRepository.findAll();

		UpdateForm form = new UpdateForm();
		Books books = booksRepository.findByBooksId(Integer.parseInt(booksId));
		//sessionから図書登録をする役職IDを取得
		int updateEmployeeId = (int) session.getAttribute("employeeId");
		Date updateDate = new Date();
		Date insertDate = books.getInsertDate();

		//冊数項目に数値以外が入力されていないかチェック

		if (isNumber(booksStock)) {
			int booksStockCount = Integer.parseInt(booksStock);

			//図書登録　同じ図書名と著者名がすでに登録されていないかチェック

			Books updatebooks = new Books((Integer.parseInt(booksId)), booksName, booksAuthor, booksStockCount,
					books.getBooksRegistration(), booksLend,
					booksRemarks, insertDate, updateEmployeeId, updateDate, classificationId);
			booksRepository.saveAndFlush(updatebooks);
			mv.addObject("message", "更新完了しました");
			mv.setViewName("books");
			return mv;
		} else
			//数値以外が入力されていた場合
			mv.addObject("message", "数値を入力してください");
		form.setBooksId(Integer.parseInt(booksId));
		form.setBooksName(booksName);
		form.setBooksAuthor(booksAuthor);
		form.setClassificationId(classificationId);
		form.setClassificationList(classificationList);
		mv.setViewName("updateBooks");
		mv.addObject("updateForm", form);

		return mv;

	}

	public boolean isNumber(String booksStock) {
		try {
			Integer.parseInt(booksStock);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}