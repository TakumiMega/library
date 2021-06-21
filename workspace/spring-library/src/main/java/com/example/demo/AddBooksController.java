package com.example.demo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.UpdateForm;

@Controller
public class AddBooksController {
	private static final String Lending_Flag = "0";//貸出可能フラグ:0
	//private static final String Non_Lending_Flag = "1";//貸出不可フラグ:1
	@Autowired
	HttpSession session;

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	ClassificationRepository classificationRepository;

	@RequestMapping("/library/addBooks")
	public ModelAndView addBooksPage(@RequestParam(name = "booksName") String booksName,
			@RequestParam(name = "booksAuthor") String booksAuthor,
			@RequestParam(name = "booksStock") String booksStock,
			@RequestParam(name = "booksLend") String booksLend,
			@RequestParam(name = "booksRemarks") String booksRemarks,
			@RequestParam(name = "classificationId") int classificationId,
			@ModelAttribute UpdateForm updateform,ModelAndView mv) {

			List<Classification> classificationList=classificationRepository.findAll();
			UpdateForm form = new UpdateForm();
			mv.addObject("updateform", updateform);
			mv.addObject("classificationList", classificationList);

		//sessionから図書登録をする役職IDを取得
			int updateEmployeeId=(int) session.getAttribute("employeeId");

			//登録に必要な今日の日付
			Date booksRegistration = new Date();
			Date insertDate = new Date();
			Date updateDate = new Date();

		//sessionにセット
		session.setAttribute("booksRegistration", booksRegistration);

		//図書名文字チェック 101字以上入力の場合エラー
		if (booksName.length() > 100) {
			mv.addObject("message", "図書名は100文字までしか入力できません");
			mv.setViewName("addBooks");
			return mv;
		}
		//著者名文字チェック 51字以上入力の場合エラー
		if (booksAuthor.length() > 50) {
			mv.addObject("message", "著者名は50文字までしか入力できません");
			mv.setViewName("addBooks");
			return mv;
		}

		//本の冊数 int型変数
		int booksStockCount = 0;

		//冊数項目に数値以外が入力されていないかチェック
		if (isNumber(booksStock)) {
			booksStockCount = Integer.parseInt(booksStock);

			//図書登録　同じ図書名と著者名がすでに登録されていないかチェック
			Books booksinfo = booksRepository.findByBooksNameAndBooksAuthor(booksName, booksAuthor);
			if (booksinfo == null) {
				Books addbooks = new Books(booksName, booksAuthor, booksStockCount, booksRegistration,booksLend,
						booksRemarks, insertDate,updateDate,updateEmployeeId,updateEmployeeId,classificationId);
				booksRepository.saveAndFlush(addbooks);
				mv.addObject("message", "登録完了しました");
				form.setClassificationList(classificationList);
				mv.addObject("updateForm", form);
				mv.setViewName("addBooks");

				return mv;
			}
			//数値以外が入力されていた場合
		} else {
			mv.addObject("message", "数値を入力してください");
			form.setBooksName(booksName);
			form.setBooksAuthor(booksAuthor);
			form.setClassificationId(classificationId);
			form.setClassificationList(classificationList);
			mv.setViewName("updateBooks");
			mv.addObject("updateForm", form);
			mv.setViewName("addBooks");

			return mv;
		}

		//すでに登録済みだった場合
		mv.addObject("message", "すでに登録されています。");
		mv.setViewName("addBooks");
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
