package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	//貸出中Flg
	final String leandingFlg_lending = "0";
	//返却済みFlg
	final String leandingFlg_return = "1";
	final Calendar calendar = Calendar.getInstance();
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Map<Integer, BooksBean> lendingMap = new HashMap<>();
	Map<Integer, LendingBean> lendMap = new HashMap<>();
	Map<Integer, LendingBean> returnMap = new HashMap<>();
	int key = 0;

	//貸出ユーザID入力
	@RequestMapping("/library/lending/userId")
	public ModelAndView lendSearchUserid(
			@RequestParam("usersId") String usersId,
			ModelAndView mv) {
		//ユーザIDの空白チェック
		if (usersId.length() == 0 || usersId == null) {
			session.removeAttribute("usersId");
			session.removeAttribute("usersName");
			mv.addObject("message", "正しいIDを入力してください");
		} else {
			//数値チェック
			if (isNumber(usersId) == true) {
				//文字数・オール0チェック
				if (usersId.length() < 8 || usersId.equals("00000000")) {
					session.removeAttribute("usersId");
					session.removeAttribute("usersName");
					mv.addObject("message", "正しいIDを入力してください");
				} else {
					//0除去,ユーザID・ユーザ名所得
					Users lendUser = usersRepository.findByUsersId(Integer.parseInt(usersId.replaceFirst("0+", "")));
					//登録されているユーザかチェック
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

	//図書検索サブウィンドウ表示
	@GetMapping(value = "/library/lendingSub")
	public ModelAndView openWinByGet(
			@RequestParam("booksName") String booksName,
			ModelAndView mv) throws DAOException {

		BooksDAO dao = new BooksDAO();
		List<BooksBean> booksList = dao.searchBooks(booksName);
		List<BooksBean> cloneList = new ArrayList<BooksBean>(booksList);
		int listnum = 0;

		for(BooksBean list: cloneList) {
			if (dao.allcasesLendingBooksId(list.getBooksId()) == false) {
				booksList.remove(listnum);
			}
			listnum++;
		}
		mv.addObject("booksName", booksName);
		mv.addObject("booksList", booksList);

		//表示させるHTMLをセット
		mv.setViewName("lendingSub");

		return mv;
	}

	//検索結果表示(貸出)
	@GetMapping(value = "/library/lending/{booksId}")
	public ModelAndView deleteHouseHold(
			@PathVariable("booksId") int booksId,
			ModelAndView mv) throws NumberFormatException, DAOException {

		BooksDAO dao = new BooksDAO();
		//ユーザID取得
		String lendUsersId = ((String) session.getAttribute("usersId")).replaceFirst("0+", "");

		//既に借りている本かのチェック
		if (dao.alreadyLendingBooksId(Integer.parseInt(lendUsersId), booksId) == false) {
			mv.addObject("message", "すでに借りている本です");
			mv.addObject("lendingMap", lendingMap);
			//表示させるHTMLをセット
			mv.setViewName("lending");
			return mv;
		}

		for (Map.Entry<Integer, BooksBean> entry : lendingMap.entrySet()) {
			//同一の本を貸出をチェック
			if (entry.getValue().getBooksId() == booksId) {
				mv.addObject("message", "同じ本は一冊までです");
				mv.addObject("lendingMap", lendingMap);
				//表示させるHTMLをセット
				mv.setViewName("lending");
				return mv;
			}

		}

		//貸出のMapに追加
		lendingMap.put(key, dao.searchBooksId(booksId));
		key = key + 1;
		mv.addObject("lendingMap", lendingMap);
		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}

	//貸出のMapから除去
	@RequestMapping(value = "/library/deleteLending/{key}")
	public ModelAndView deleteLending(
			@PathVariable("key") int key,
			ModelAndView mv) throws NumberFormatException, DAOException {

		//Keyから削除
		lendingMap.remove(key);
		mv.addObject("lendingMap", lendingMap);

		//表示させるHTMLをセット
		mv.setViewName("lending");

		return mv;
	}

	//貸出処理
	@RequestMapping(value = "/library/lended", method = RequestMethod.POST)
	public ModelAndView lended(
			ModelAndView mv) throws NumberFormatException, DAOException {

		//貸出のMapが空ではないかチェック
		if (lendingMap.isEmpty()) {
			mv.addObject("message", "貸出する本がありません");
		} else {
			//現在の年月日を取得・セット
			Date nowDay = calendar.getTime();
			//7日後の年月日を所得・セット
			calendar.add(Calendar.DATE, 7);
			Date returnDay = calendar.getTime();
			//ユーザID取得
			String lendUsersId = ((String) session.getAttribute("usersId")).replaceFirst("0+", "");

			//貸出Mapから全て貸出を行う
			for (Map.Entry<Integer, BooksBean> entry : lendingMap.entrySet()) {
				Lending lending = new Lending(nowDay, returnDay, leandingFlg_lending, nowDay,
						(int) session.getAttribute("employeeId"), nowDay, (int) session.getAttribute("employeeId"),
						Integer.parseInt(lendUsersId), entry.getValue().getBooksId());
				lendingRepository.saveAndFlush(lending);
			}
			//メッセージ設定
			mv.addObject("message", "貸出しました");
			calendar.add(Calendar.DATE, -7);
			//セッション削除
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
		//ユーザIDの空白チェック
		if (usersId.length() == 0 || usersId == null) {
			session.removeAttribute("usersId");
			session.removeAttribute("usersName");
			mv.addObject("message", "正しいIDを入力してください");
		} else {
			//数値チェック
			if (isNumber(usersId) == true) {
				//文字数・オール0チェック
				if (usersId.length() < 8 || usersId.equals("00000000")) {
					session.removeAttribute("usersId");
					session.removeAttribute("usersName");
					mv.addObject("message", "正しいIDを入力してください");
				} else {
					//0除去,ユーザID・ユーザ名所得
					Users lendUser = usersRepository.findByUsersId(Integer.parseInt(usersId.replaceFirst("0+", "")));
					if (lendUser != null) {
						session.setAttribute("usersId", usersId);
						session.setAttribute("usersName", lendUser.getUsersName());
						LendingDAO dao = new LendingDAO();
						//貸出中の本をMapで取得
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

	//貸出中Mapから返却予定Mapに本を移動
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

	//返却予定Mapから貸出中Mapに本を移動
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

	//返却処理
	@RequestMapping(value = "/library/returned", method = RequestMethod.POST)
	public ModelAndView returned(
			ModelAndView mv) throws NumberFormatException, DAOException {
		//返却予定Mapが空かチェックする
		if (returnMap.isEmpty()) {
			mv.addObject("message", "返却する本がありません");
			mv.addObject("lendMap", lendMap);
		} else {
			//現在の年月日を取得・セット
			Date nowDay = calendar.getTime();
			//返却処理
			for (Entry<Integer, LendingBean> entry : returnMap.entrySet()) {
				//貸出情報セット
				Lending lending = new Lending(entry.getValue().getLendingId(), entry.getValue().getLendingLendDate(),
						entry.getValue().getLendingReturnDate(),
						leandingFlg_return, entry.getValue().getInsertDate(), entry.getValue().getInsertEmployeeId(),
						nowDay, (int) session.getAttribute("employeeId"),
						entry.getValue().getUsersId(), entry.getValue().getBooksId());
				//更新処理
				lendingRepository.saveAndFlush(lending);
			}
			mv.addObject("message", "返却しました");
			//セッション削除
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

	//返却超過処理
	@RequestMapping("/library/returnOver/searchUser")
	public ModelAndView returnOverSearchUserid(
			@RequestParam("usersId") String usersId,
			ModelAndView mv) throws DAOException {

		LendingDAO lendao = new LendingDAO();
		//ユーザIDの空白チェック
		if (usersId.length() == 0 || usersId == null) {
			session.removeAttribute("usersId");
			//全件表示
			List<LendingBean> lendingList = lendao.searchFirstLendingOverList();
			mv.addObject("lendingList", lendingList);
		} else {
			//数値チェック
			if (isNumber(usersId) == true) {
				//文字数・オール0チェック
				if (usersId.length() < 8 || usersId.equals("00000000")) {
					session.removeAttribute("usersId");
					mv.addObject("message", "正しいIDを入力してください");
				} else {
					//0除去,ユーザID・ユーザ名所得
					Users lendUser = usersRepository.findByUsersId(Integer.parseInt(usersId.replaceFirst("0+", "")));
					if (lendUser != null) {
						session.setAttribute("usersId", usersId);
						//返却超過検索
						List<LendingBean> lendingList = lendao
								.searchLendingOverList(Integer.parseInt(usersId.replaceFirst("0+", "")));
						//画面に超過リストをセット
						mv.addObject("lendingList", lendingList);
					} else {
						//セッション削除
						session.removeAttribute("usersId");
						mv.addObject("message", "その会員番号の利用者は存在しません");
					}
				}
			} else {
				//セッション削除
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
