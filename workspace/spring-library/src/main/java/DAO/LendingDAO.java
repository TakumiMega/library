package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.LendingBean;

public class LendingDAO {
	private Connection con;
	final Calendar calendar = Calendar.getInstance();
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public LendingDAO() throws DAOException {
		getConnection();
	}

	public Map<Integer, LendingBean> searchLendingBooks(int userId) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT l.LENDING_ID,l.LENDING_LEND_DATE,l.LENDING_RETURN_DATE,l.INSERT_DATE,l.INSERT_EMPLOYEE_ID,l.USERS_ID,b.BOOKS_ID,b.BOOKS_NAME,b.BOOKS_AUTHOR "
					+ "FROM LENDING l JOIN BOOKS b ON l.BOOKS_ID = b.BOOKS_ID "
					+ "WHERE l.USERS_ID = ? AND l.LENDING_FLG = '0'";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1, userId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			Map<Integer, LendingBean> map = new HashMap<>();
			int key = 0;
			while (rs.next()) {
				int lendingId = rs.getInt("lending_id");
				Date lendingLendDate = rs.getDate("lending_lend_date");
				Date lendingReturnDate = rs.getDate("lending_return_date");
				Date insertDate = rs.getDate("insert_date");
				int insertEmployeeId = rs.getInt("insert_employee_id");
				int usersId = rs.getInt("users_id");
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				LendingBean bean = new LendingBean(lendingId, lendingLendDate, lendingReturnDate, insertDate,
						insertEmployeeId, usersId, booksId, booksName, booksAuthor);
				map.put(key, bean);
				key = key + 1;
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	public List<LendingBean> searchFirstLendingList() throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT b.BOOKS_ID,b.BOOKS_NAME,b.BOOKS_AUTHOR,c.CLASSIFICATION_NAME,u.USERS_NAME,l.LENDING_LEND_DATE,l.LENDING_RETURN_DATE "
					+ "FROM LENDING l LEFT OUTER JOIN USERS u ON l.USERS_ID = u.USERS_ID "
					+ "LEFT OUTER JOIN books b ON l.BOOKS_ID = b.BOOKS_ID "
					+ "LEFT OUTER JOIN CLASSIFICATION c ON b.CLASSIFICATION_ID = c.CLASSIFICATION_ID "
					+ "WHERE l.LENDING_FLG = '0'";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<LendingBean> list = new ArrayList<LendingBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				String classificationName = rs.getString("classification_name");
				String usersName = rs.getString("users_name");
				Date lendingLendDate = rs.getDate("lending_lend_date");
				Date lendingReturnDate = rs.getDate("lending_return_date");
				LendingBean bean = new LendingBean(booksId, booksName, booksAuthor,classificationName,usersName,dateFormat.format(lendingLendDate),dateFormat.format(lendingReturnDate));
				list.add(bean);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	public List<LendingBean> searchLendingListBooksNameBooksAuthorAll(String bookName, String bookAuthor) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT b.BOOKS_ID,b.BOOKS_NAME,b.BOOKS_AUTHOR,c.CLASSIFICATION_NAME,u.USERS_NAME,l.LENDING_LEND_DATE,l.LENDING_RETURN_DATE "
					+ "FROM LENDING l LEFT OUTER JOIN USERS u ON l.USERS_ID = u.USERS_ID "
					+ "LEFT OUTER JOIN books b ON l.BOOKS_ID = b.BOOKS_ID "
					+ "LEFT OUTER JOIN CLASSIFICATION c ON b.CLASSIFICATION_ID = c.CLASSIFICATION_ID "
					+ "WHERE l.LENDING_FLG = '0' AND BOOKS_NAME LIKE ? AND BOOKS_AUTHOR LIKE ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookName + "%");
			st.setString(2, "%" + bookAuthor + "%");
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<LendingBean> list = new ArrayList<LendingBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				String classificationName = rs.getString("classification_name");
				String usersName = rs.getString("users_name");
				Date lendingLendDate = rs.getDate("lending_lend_date");
				Date lendingReturnDate = rs.getDate("lending_return_date");
				LendingBean bean = new LendingBean(booksId, booksName, booksAuthor,classificationName,usersName,dateFormat.format(lendingLendDate),dateFormat.format(lendingReturnDate));
				list.add(bean);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	public List<LendingBean> searchLendingListBooksNameBooksAuthorClass(String bookName, String bookAuthor,int classification) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT b.BOOKS_ID,b.BOOKS_NAME,b.BOOKS_AUTHOR,c.CLASSIFICATION_NAME,u.USERS_NAME,l.LENDING_LEND_DATE,l.LENDING_RETURN_DATE "
					+ "FROM LENDING l LEFT OUTER JOIN USERS u ON l.USERS_ID = u.USERS_ID "
					+ "LEFT OUTER JOIN books b ON l.BOOKS_ID = b.BOOKS_ID "
					+ "LEFT OUTER JOIN CLASSIFICATION c ON b.CLASSIFICATION_ID = c.CLASSIFICATION_ID "
					+ "WHERE l.LENDING_FLG = '0' AND BOOKS_NAME LIKE ? AND BOOKS_AUTHOR LIKE ? AND c.CLASSIFICATION_ID = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookName + "%");
			st.setString(2, "%" + bookAuthor + "%");
			st.setInt(3, classification);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<LendingBean> list = new ArrayList<LendingBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				String classificationName = rs.getString("classification_name");
				String usersName = rs.getString("users_name");
				Date lendingLendDate = rs.getDate("lending_lend_date");
				Date lendingReturnDate = rs.getDate("lending_return_date");
				LendingBean bean = new LendingBean(booksId, booksName, booksAuthor,classificationName,usersName,dateFormat.format(lendingLendDate),dateFormat.format(lendingReturnDate));
				list.add(bean);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	public List<LendingBean> searchFirstLendingOverList() throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT l.LENDING_ID,u.USERS_ID,u.USERS_NAME,b.BOOKS_NAME,l.LENDING_LEND_DATE,l.LENDING_RETURN_DATE  "
					+ "FROM LENDING l LEFT OUTER JOIN USERS u ON l.USERS_ID = u.USERS_ID LEFT OUTER JOIN books b ON l.BOOKS_ID = b.BOOKS_ID "
					+ "WHERE l.LENDING_FLG = '0' AND ? > l.LENDING_RETURN_DATE::character varying";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			String nowDay = dateFormat.format(calendar.getTime());
			st.setString(1, nowDay);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<LendingBean> list = new ArrayList<LendingBean>();
			while (rs.next()) {
				int lendingId = rs.getInt("lending_id");
				int usersId = rs.getInt("users_id");
				String usersName = rs.getString("users_name");
				String booksName = rs.getString("books_name");
				Date lendingLendDate = rs.getDate("lending_lend_date");
				Date lendingReturnDate = rs.getDate("lending_return_date");
				LendingBean bean = new LendingBean(lendingId,usersId,usersName,booksName,dateFormat.format(lendingLendDate),dateFormat.format(lendingReturnDate));
				list.add(bean);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}


	public List<LendingBean> searchLendingOverList(int userId) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT l.LENDING_ID,u.USERS_ID,u.USERS_NAME,b.BOOKS_NAME,l.LENDING_LEND_DATE,l.LENDING_RETURN_DATE  "
					+ "FROM LENDING l LEFT OUTER JOIN USERS u ON l.USERS_ID = u.USERS_ID LEFT OUTER JOIN books b ON l.BOOKS_ID = b.BOOKS_ID "
					+ "WHERE l.LENDING_FLG = '0' AND ? > l.LENDING_RETURN_DATE::character varying AND l.USERS_ID = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			String nowDay = dateFormat.format(calendar.getTime());
			st.setString(1, nowDay);
			st.setInt(2, userId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<LendingBean> list = new ArrayList<LendingBean>();
			while (rs.next()) {
				int lendingId = rs.getInt("lending_id");
				int usersId = rs.getInt("users_id");
				String usersName = rs.getString("users_name");
				String booksName = rs.getString("books_name");
				Date lendingLendDate = rs.getDate("lending_lend_date");
				Date lendingReturnDate = rs.getDate("lending_return_date");
				LendingBean bean = new LendingBean(lendingId,usersId,usersName,booksName,dateFormat.format(lendingLendDate),dateFormat.format(lendingReturnDate));
				list.add(bean);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	private void getConnection() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			// URL、ユーザ名、パスワードの設定
			String url = "jdbc:postgresql:team";
			String user = "student";
			String pass = "himitu";
			// データベースへの接続
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("接続に失敗しました。");
		}
	}

	private void close() throws SQLException {
		if (con != null) {
			con.close();
			con = null;
		}
	}
}
