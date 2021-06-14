package com.example.demo;



import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddBooksController {
	private static final String Lending_Flag="0";//貸出可能フラグ:0
	private static final String Non_Lending_Flag="1";//貸出不可フラグ:1
	@Autowired
	HttpSession session;

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	ClassificationRepository classificationRepository;

	@RequestMapping("/")
	public String login() {
		// セッション情報はクリアする
		session.invalidate();
		return "addBooks";
	}

	@RequestMapping("/library/addBooks")
	public ModelAndView addBooksPage(@RequestParam(name = "booksName") String booksName,
			@RequestParam(name = "booksAuthor") String booksAuthor,
			@RequestParam(name = "booksStock") int booksStock,
			@RequestParam(name = "booksRemarks") String booksRemarks,
			@RequestParam(name = "classificationId") int classificationId, ModelAndView mv) {


		//図書登録　同じ図書名と著者名がすでに登録されていないかチェック
		Books booksinfo = booksRepository.findByBooksNameAndBooksAuthor(booksName, booksAuthor);
		Date booksRegistration=new Date();
		String booksLend=Lending_Flag;
		if (booksinfo == null) {
			Books addbooks = new Books(booksName, booksAuthor, booksStock,booksRegistration,booksLend,booksRemarks, classificationId);
			booksRepository.saveAndFlush(addbooks);
			mv.addObject("message", "登録完了しました");
			mv.setViewName("addBooks");

			return mv;
		}

		//すでに登録済みだった場合
		mv.addObject("message", "すでに登録されています。");
		mv.setViewName("addBooks");
		return mv;
	}

/*	public boolean isNumber(String booksStock) {
		try {
			Integer.parseInt(booksStock);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}*/
	}

