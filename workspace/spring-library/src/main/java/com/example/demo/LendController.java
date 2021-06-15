package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.BooksBean;
import DAO.BooksDAO;
import DAO.DAOException;

@Controller
public class LendController {
//
//	List<BooksBean> lendingList = new ArrayList<>();
	Map<Integer, BooksBean> lendingMap = new HashMap<>();
	int key = 0;
	@RequestMapping("/")
	public String loginPage() {
		return "lending";
	}

	@GetMapping(value = "/library/lendingSub")
	public ModelAndView openWinByGet(
			@RequestParam("booksName") String booksName,
			ModelAndView mv) throws DAOException{

		BooksDAO dao = new BooksDAO();
		List<BooksBean> booksList = dao.searchBooks(booksName);
		mv.addObject("booksName", booksName);
		mv.addObject("booksList", booksList);

		//表示させるHTMLをセット
		mv.setViewName("lendingSub");

		return mv;
	}

	@GetMapping(value = "/library/lending/{booksId}")
	public ModelAndView deleteHouseHold(
			@PathVariable("booksId") int booksId,
			ModelAndView mv) throws NumberFormatException, DAOException {
		BooksDAO dao = new BooksDAO();
			lendingMap.put(key, dao.searchBooksId(booksId));
			key = key+1;
		mv.addObject("lendingMap",lendingMap);

		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}

	@RequestMapping(value = "/library/deleteLending/{key}")
	public ModelAndView deleteLending(
			@PathVariable("key") int key,
			ModelAndView mv) throws NumberFormatException, DAOException {

		lendingMap.remove(key);
		mv.addObject("lendingMap",lendingMap);

		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}
}
