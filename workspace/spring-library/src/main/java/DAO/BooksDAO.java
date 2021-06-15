package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.BooksBean;

public class BooksDAO {
	private Connection con;

	public BooksDAO() throws DAOException {
		getConnection();
	}

	public List<BooksBean> searchBooks(String bookName) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author FROM books WHERE books_name LIKE ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1,"%"+bookName+"%");
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				BooksBean bean = new BooksBean(booksId,booksName,booksAuthor);
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

	public BooksBean searchBooksId(int bookId) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author FROM books WHERE books_id = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1,bookId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			BooksBean list = null;
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				BooksBean bean = new BooksBean(booksId,booksName,booksAuthor);
				list=bean;
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
