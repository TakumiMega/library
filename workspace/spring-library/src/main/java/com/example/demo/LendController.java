package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.BooksBean;
import Bean.LendingBean;
import DAO.BooksDAO;
import DAO.DAOException;
import DAO.LendingDAO;

@Controller
public class LendController {

	@Autowired
	HttpSession session;

	@Autowired
	LendingRepository lendingRepository;

	@Autowired
	UsersRepository usersRepository;

	final String leandingFlg_lending = "0";
	final String leandingFlg_return = "1";
	final Calendar calendar = Calendar.getInstance();
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Map<Integer, BooksBean> lendingMap = new HashMap<>();
	Map<Integer, LendingBean> lendMap = new HashMap<>();
	Map<Integer, LendingBean> returnMap = new HashMap<>();
	int key = 0;

	//ユーザID入力
	@RequestMapping("/library/lending/userId")
	public ModelAndView lendSearchUserid(
			@RequestParam("usersId") String usersId,
			ModelAndView mv) {
		if (usersId.length() == 0 || usersId == null) {
			session.removeAttribute("usersId");
			session.removeAttribute("usersName");
			mv.addObject("message", "正しいIDを入力してください");
		} else {
			if (isNumber(usersId) == true) {
				if (usersId.length() < 8 || usersId.equals("00000000")) {
					session.removeAttribute("usersId");
					session.removeAttribute("usersName");
					mv.addObject("message", "正しいIDを入力してください");
				} else {
					Users lendUser = usersRepository.findByUsersId(Integer.parseInt(usersId.replaceFirst("0+", "")));
					if (lendUser != null) {
						session.setAttribute("usersId", usersId);
						session.setAttribute("usersName", lendUser.getUsersName());
					} else {
						session.removeAttribute("usersId");
						session.removeAttribute("usersName");
						mv.addObject("message", "その会員番号の利用者は存在しません");
					}
				}
			} else {
				mv.addObject("message", "正しいIDを入力してください");
			}
		}
		mv.setViewName("lending");
		return mv;
	}

	@GetMapping(value = "/library/lendingSub")
	public ModelAndView openWinByGet(
			@RequestParam("booksName") String booksName,
			ModelAndView mv) throws DAOException {

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
		String lendUsersId = ((String) session.getAttribute("usersId")).replaceFirst("0+", "");

		if (dao.alreadyLendingBooksId(Integer.parseInt(lendUsersId), booksId) == false) {
			mv.addObject("message", "すでに借りている本です");
			mv.addObject("lendingMap", lendingMap);
			//表示させるHTMLをセット
			mv.setViewName("lending");
			return mv;
		}

		for (Map.Entry<Integer, BooksBean> entry : lendingMap.entrySet()) {
			if (entry.getValue().getBooksId() == booksId) {
				mv.addObject("message", "同じ本は一冊までです");
				mv.addObject("lendingMap", lendingMap);
				//表示させるHTMLをセット
				mv.setViewName("lending");
				return mv;
			}

		}

		lendingMap.put(key, dao.searchBooksId(booksId));
		key = key + 1;
		mv.addObject("lendingMap", lendingMap);
		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}

	@RequestMapping(value = "/library/deleteLending/{key}")
	public ModelAndView deleteLending(
			@PathVariable("key") int key,
			ModelAndView mv) throws NumberFormatException, DAOException {

		lendingMap.remove(key);
		mv.addObject("lendingMap", lendingMap);

		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}

	@RequestMapping(value = "/library/lended", method = RequestMethod.POST)
	public ModelAndView lended(
			ModelAndView mv) throws NumberFormatException, DAOException {

		if (lendingMap.isEmpty()) {
			mv.addObject("message", "貸出する本がありません");
		} else {
			//現在の年月日を取得・セット
			Date nowDay = calendar.getTime();
			calendar.add(Calendar.DATE, 7);
			Date returnDay = calendar.getTime();
			String lendUsersId = ((String) session.getAttribute("usersId")).replaceFirst("0+", "");
			for (Map.Entry<Integer, BooksBean> entry : lendingMap.entrySet()) {
				Lending lending = new Lending(nowDay, returnDay, leandingFlg_lending, nowDay, (int) session.getAttribute("employeeId"), nowDay, (int) session.getAttribute("employeeId"),
						Integer.parseInt(lendUsersId), entry.getValue().getBooksId());
				lendingRepository.saveAndFlush(lending);
			}
			mv.addObject("message", "貸出しました");
			calendar.add(Calendar.DATE, -7);
			session.removeAttribute("usersId");
			session.removeAttribute("usersName");
			lendingMap.clear();
			key = 0;
		} //表示させるHTMLをセット
		mv.setViewName("lending");
		return mv;
	}

	//ユーザID入力
	@RequestMapping("/library/returning/userId")
	public ModelAndView returnSearchUserid(
			@RequestParam("usersId") String usersId,
			ModelAndView mv) throws DAOException {
		if (usersId.length() == 0 || usersId == null) {
			session.removeAttribute("usersId");
			session.removeAttribute("usersName");
			mv.addObject("message", "正しいIDを入力してください");
		} else {
			if (isNumber(usersId) == true) {
				if (usersId.length() < 8) {
					session.removeAttribute("usersId");
					session.removeAttribute("usersName");
					mv.addObject("message", "正しいIDを入力してください");
				} else {
					Users lendUser = usersRepository.findByUsersId(Integer.parseInt(usersId.replaceFirst("0+", "")));
					if (lendUser != null) {
						session.setAttribute("usersId", usersId);
						session.setAttribute("usersName", lendUser.getUsersName());
						LendingDAO dao = new LendingDAO();
						lendMap = dao.searchLendingBooks(Integer.parseInt(usersId.replaceFirst("0+", "")));
						mv.addObject("lendMap", lendMap);
					} else {
						session.removeAttribute("usersId");
						session.removeAttribute("usersName");
						mv.addObject("message", "その会員番号の利用者は存在しません");
					}
				}
			} else {
				session.removeAttribute("usersId");
				session.removeAttribute("usersName");
				mv.addObject("message", "正しいIDを入力してください");
			}
		}
		mv.setViewName("return");
		return mv;
	}

	@RequestMapping(value = "/library/returning/{key}")
	public ModelAndView addreturning(
			@PathVariable("key") int key,
			ModelAndView mv) throws NumberFormatException, DAOException {

		returnMap.put(key, lendMap.get(key));
		lendMap.remove(key);

		mv.addObject("lendMap", lendMap);
		mv.addObject("returnMap", returnMap);
		//表示させるHTMLをセット
		mv.setViewName("return");

		return mv;
	}

	@RequestMapping(value = "/library/returningback/{key}")
	public ModelAndView backreturning(
			@PathVariable("key") int key,
			ModelAndView mv) throws NumberFormatException, DAOException {

		lendMap.put(key, returnMap.get(key));
		returnMap.remove(key);

		mv.addObject("lendMap", lendMap);
		mv.addObject("returnMap", returnMap);
		//表示させるHTMLをセット
		mv.setViewName("return");

		return mv;
	}

	@RequestMapping(value = "/library/returned", method = RequestMethod.POST)
	public ModelAndView returned(
			ModelAndView mv) throws NumberFormatException, DAOException {
		if (returnMap.isEmpty()) {
			mv.addObject("message", "返却する本がありません");
			mv.addObject("lendMap", lendMap);
		} else {
			//現在の年月日を取得・セット
			Date nowDay = calendar.getTime();

			for (Entry<Integer, LendingBean> entry : returnMap.entrySet()) {
				Lending lending = new Lending(entry.getValue().getLendingId(), entry.getValue().getLendingLendDate(),
						entry.getValue().getLendingReturnDate(),
						leandingFlg_return, entry.getValue().getInsertDate(), entry.getValue().getInsertEmployeeId(),
						nowDay, (int) session.getAttribute("employeeId"),
						entry.getValue().getUsersId(), entry.getValue().getBooksId());
				lendingRepository.saveAndFlush(lending);
			}
			mv.addObject("message", "返却しました");
			session.removeAttribute("usersId");
			session.removeAttribute("usersName");
			lendMap.clear();
			returnMap.clear();
			key = 0;
		}
		//表示させるHTMLをセット
		mv.setViewName("return");
		return mv;
	}

	@RequestMapping("/library/returnOver/searchUser")
	public ModelAndView returnOverSearchUserid(
			@RequestParam("usersId") String usersId,
			ModelAndView mv) throws DAOException {

		LendingDAO lendao = new LendingDAO();
		if (usersId.length() == 0 || usersId == null) {
			session.removeAttribute("usersId");
			List<LendingBean> lendingList = lendao.searchFirstLendingOverList();
			mv.addObject("lendingList",lendingList);
		} else {
			if (isNumber(usersId) == true) {
				if (usersId.length() < 8  || usersId.equals("00000000")) {
					session.removeAttribute("usersId");
					mv.addObject("message", "正しいIDを入力してください");
				} else {
					Users lendUser = usersRepository.findByUsersId(Integer.parseInt(usersId.replaceFirst("0+", "")));
					if (lendUser != null) {
						session.setAttribute("usersId", usersId);
						List<LendingBean> lendingList = lendao.searchLendingOverList(Integer.parseInt(usersId.replaceFirst("0+", "")));
						mv.addObject("lendingList",lendingList);
					} else {
						session.removeAttribute("usersId");
						mv.addObject("message", "その会員番号の利用者は存在しません");
					}
				}
			} else {
				session.removeAttribute("usersId");
				mv.addObject("message", "正しいIDを入力してください");
			}
		}
		mv.setViewName("returnOver");
		return mv;
	}

	public boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
