package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import Bean.EmployeeListBean;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDAO {
	private Connection con;
	
	public EmployeeDAO() throws DAOException {
		getConnection();
	}
	
	public List<EmployeeListBean> findAll() throws DAOException {
		if (con == null)
			getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			// SQL文の作成
			String sql = "SELECT e.employee_id,e.employee_name,p.position_name"
					+ " FROM EMPLOYEE e JOIN POSITION p ON e.position_id = p.position_id ORDER BY e.employee_id";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<EmployeeListBean> employeeList = new ArrayList<EmployeeListBean>();
			while (rs.next()) {
				int employeeId = rs.getInt("employee_id");
				String employeeName = rs.getString("employee_name");
				String positionName = rs.getString("position_name");
				EmployeeListBean bean = new EmployeeListBean(employeeId, employeeName, positionName);
				employeeList.add(bean);
			}
			// 商品一覧をListとして返す
			return employeeList;
			
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
	
	public List<EmployeeListBean> searchEmployeeId(int searchEmployeeId) throws DAOException {
		if (con == null)
			getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			// SQL文の作成
			String sql = "SELECT e.employee_id,e.employee_name,p.position_name"
					+ " FROM EMPLOYEE e JOIN POSITION p ON e.position_id = p.position_id"
					+ " WHERE e.employee_id = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// ソートキーの指定
			st.setInt(1, searchEmployeeId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得
			List<EmployeeListBean> employeeList = new ArrayList<EmployeeListBean>();
			while (rs.next()) {
				int employeeId = rs.getInt("employee_id");
				String employeeName = rs.getString("employee_name");
				String positionName = rs.getString("position_name");
				EmployeeListBean bean = new EmployeeListBean(employeeId, employeeName, positionName);
				employeeList.add(bean);
			}
			// 商品一覧をListとして返す
			return employeeList;
			
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
