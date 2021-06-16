package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Bean.LendingBean;

public class LendingDAO {
	private Connection con;

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
