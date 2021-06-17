package com.example.demo;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UpdateController {
	@Autowired
	HttpSession session;

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	ClassificationRepository classificationRepository;

	@RequestMapping("/library/updateBooksPage/{booksId}")
	public ModelAndView updateBooks(ModelAndView mv, @PathVariable("booksId") int booksId) {
		Books books = booksRepository.findByBooksId(booksId);
		mv.addObject("books", books);
		mv.setViewName("updateBooks");
		return mv;
	}

	@RequestMapping("/library/updateBooks/{booksId}")
	public ModelAndView updateBooksPage(@PathVariable("booksId") int booksId,
			@RequestParam(name = "booksName") String booksName,
			@RequestParam(name = "booksAuthor") String booksAuthor,
			@RequestParam(name = "booksStock") int booksStock,
			@RequestParam(name = "booksLend") String booksLend,
			@RequestParam(name = "booksRemarks") String booksRemarks,
			@RequestParam(name = "classificationId") int classificationId,
			ModelAndView mv) {

		@SuppressWarnings("unchecked")
		/*List<Books> updatelist=(List<Books>) booksRepository.findByBooksId(booksId);*/
		Books books = booksRepository.findByBooksId(booksId);
		Date updateDate = new Date();
		//図書名文字チェック 101字以上入力の場合エラー
		if (books.getBooksName().length() > 100) {
			mv.addObject("message", "図書名は100文字までしか入力できません");
			mv.setViewName("updateBooks");
			return mv;
		}
		//著者名文字チェック 51字以上入力の場合エラー
		if (books.getBooksAuthor().length() > 50) {
			mv.addObject("message", "著者名は50文字までしか入力できません");
			mv.setViewName("updateBooks");
			return mv;
		}

		//冊数項目に数値以外が入力されていないかチェック
		/*	if (isNumber(books.getBooksStock())) {
				booksStockCount = Integer.parseInt(books.getBooksStock());
		*/
		//図書登録　同じ図書名と著者名がすでに登録されていないかチェック

		Books updatebooks = new Books(booksId, booksName, booksAuthor, booksStock,
				books.getBooksRegistration(), booksLend,
				booksRemarks, updateDate,classificationId);
		booksRepository.saveAndFlush(updatebooks);
		mv.addObject("message", "更新完了しました");
		mv.setViewName("addBooks");
		return mv;
	}

	/*	}else
			//数値以外が入力されていた場合
			mv.addObject("message", "数値を入力してください");
			mv.setViewName("addBooks");
			return mv;
		}
	*/
	public boolean isNumber(String booksStock) {
		try {
			Integer.parseInt(booksStock);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}