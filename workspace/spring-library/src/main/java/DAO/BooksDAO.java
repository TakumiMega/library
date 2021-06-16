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

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author FROM books WHERE books_name LIKE ? AND books_lend = '0' AND books_stock > "
					+ "(select count(*) as lendcount from lending AS l,books AS b "
					+ "where l.books_id = b.books_id AND l.lending_flg = '0')";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookName + "%");
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor);
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

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author FROM books WHERE books_id = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1, bookId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			BooksBean list = null;
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor);
				list = bean;
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


	public boolean alreadyLendingBooksId(int usersId,int bookId) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String lending_id = null;
		try
		{
			// SQL文の作成
			String sql = "select lending_id from lending where users_id = ? AND books_id = ? AND lending_flg = '0'";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1,usersId);
			st.setInt(2,bookId);
			// SQLの実行
			rs = st.executeQuery();
			while (rs.next()) {
				lending_id = String.valueOf(rs.getInt("lending_id"));
			}
			if(lending_id ==null||lending_id.length() == 0) {
				return true;
			}

			return false;
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

	//図書名検索
	public List<BooksBean> searchBooksName(String bookName) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name FROM "
					+ "books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id "
					+ "WHERE books_name LIKE ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookName + "%");
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");

				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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

	//著者名検索
	public List<BooksBean> searchBooksAuthor(String bookAuthor) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name FROM "
					+ "books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id "
					+ "WHERE books_author LIKE ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookAuthor + "%");
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");

				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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

	//分類名
	public List<BooksBean> searchBooksclassificationId(int classificationId) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name FROM "
					+ "books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id "
					+ "WHERE classification.classification_id = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1, classificationId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");

				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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

	//図書名,著者名検索
	public List<BooksBean> searchBooksNameAndAuthor(String bookName, String bookAuthor) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name FROM "
					+ "books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id "
					+ "WHERE books_name LIKE ? AND books_author LIKE ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookName + "%");
			st.setString(2, "%" + bookAuthor + "%");
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");

				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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

	//図書名,著者名,分類名検索
	public List<BooksBean> searchBooksNameAndAuthorAndclassificationId(String bookName, String bookAuthor,
			int classificationId) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name FROM "
					+ "books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id "
					+ "WHERE books_name LIKE ? AND books_author LIKE ? AND classification_id = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookName + "%");
			st.setString(2, "%" + bookAuthor + "%");
			st.setInt(3, classificationId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");

				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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

	//図書名,分類名検索
	public List<BooksBean> searchBooksNameAndclassificationId(String bookName, int classificationId)
			throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name FROM "
					+ "books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id "
					+ "WHERE books_name LIKE ? AND classification_id = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookName + "%");
			st.setInt(2, classificationId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");

				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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

	//著者名,分類名検索
	public List<BooksBean> searchBooksAuthorAndclassificationId(String bookAuthor, int classificationId)
			throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name FROM "
					+ "books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id "
					+ "WHERE books_author LIKE ? AND classification_id = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, "%" + bookAuthor + "%");
			st.setInt(2, classificationId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");

				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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

	//一覧表示
	public List<BooksBean> searchBooksInfo(String bookName) throws DAOException {
		if (con == null)
			getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT books_id, books_name, books_author,books_stock,books_remarks,classification_name "
					+ "FROM books LEFT OUTER JOIN classification ON books.classification_id=classification.classification_id";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<BooksBean> list = new ArrayList<BooksBean>();
			while (rs.next()) {
				int booksId = rs.getInt("books_id");
				String booksName = rs.getString("books_name");
				String booksAuthor = rs.getString("books_author");
				int booksStock = rs.getInt("books_stock");
				String booksRemarks = rs.getString("books_remarks");
				String classificationName = rs.getString("classification_name");
				BooksBean bean = new BooksBean(booksId, booksName, booksAuthor, booksStock, booksRemarks,
						classificationName);
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
