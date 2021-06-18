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
			@PathVariable("booksId") int booksId,
			@ModelAttribute UpdateForm updateform) {
		List<Classification> classificationList = classificationRepository.findAll();
		Books books = booksRepository.findByBooksId(booksId);
		mv.addObject("books", books);
		mv.addObject("updateform", updateform);
		mv.addObject("classificationList", classificationList);
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

		@SuppressWarnings("unchecked")
		/*List<Books> updatelist=(List<Books>) booksRepository.findByBooksId(booksId);*/
		Books books = booksRepository.findByBooksId(Integer.parseInt(booksId));
		//sessionから図書登録をする役職IDを取得
		int insertEmployeeId = (int) session.getAttribute("employeeId");
		Date updateDate = new Date();
		Date insertDate = books.getInsertDate();
		//冊数項目に数値以外が入力されていないかチェック
		if (isNumber(booksStock)) {
			int booksStockCount = Integer.parseInt(booksStock);

			//図書登録　同じ図書名と著者名がすでに登録されていないかチェック

			Books updatebooks = new Books((Integer.parseInt(booksId)), booksName, booksAuthor, booksStockCount,
					books.getBooksRegistration(), booksLend,
					booksRemarks, insertDate, insertEmployeeId, updateDate, classificationId);
			booksRepository.saveAndFlush(updatebooks);
			mv.addObject("message", "更新完了しました");
			mv.setViewName("books");
			return mv;
		} else
			//数値以外が入力されていた場合
			mv.addObject("message", "数値を入力してください");
		mv.setViewName("updateBooks");
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