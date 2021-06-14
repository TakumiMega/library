package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddBooksController {
	@Autowired
	HttpSession session;

	@Autowired
	BooksRepository booksRepository;

	@RequestMapping("/library/addBooksPage")
	public ModelAndView addBooksPage(@RequestParam(name = "booksName") String booksName,
			@RequestParam(name = "booksAuthor") String booksAuthor,
			@RequestParam(name = "books_stock") int booksStock, @RequestParam(name = "booksRemark") String booksRemark,
			@RequestParam(name = "classificationId") int classificationId, ModelAndView mv) {

		//入力値チェック
		if (booksName.equals("") || booksAuthor.equals("") || booksStock==0 || classificationId==0) {
			mv.addObject("message", "未入力の部分があります");
			mv.setViewName("addBooks");
			return mv;
		}

		//図書登録　同じ図書名と著者名がすでに登録されていないかチェック
		Books booksinfo = booksRepository.findByBooksNameAndBooksAuthor(booksName,booksAuthor);
		if (booksinfo == null) {
			Books addbooks = new Books(booksName,booksAuthor,booksStock,booksRemark,classificationId);
			booksRepository.saveAndFlush(addbooks);
			mv.addObject("message", "登録完了しました");
			mv.setViewName("top");

			return mv;
		}

		mv.addObject("message", "すでに登録されている");
		mv.setViewName("regist");
		return mv;
	}

}
