package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//社員テーブル
@Entity
@Table(name="employee")
public class Employee {

	//社員ID
	@Id
	@Column(name="employee_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;
	
	//社員名
	@Column(name="employee_name")
	private String employeeName;
	
	//社員パスワード
	@Column(name="employee_pass")
	private String employeePass;
	
	//社員登録日
	@Column(name="insert_date")
	private Date insertDate;
	
	//社員登録ユーザーID
	@Column(name="insert_employee_id")
	private int insertEmployeeId;
	
	//更新日
	@Column(name="update_date")
	private Date updateDate;
		
	//更新ユーザーID
	@Column(name="update_employee_id")
	private int updateEmployeeId;
		
	//役職ID
	@Column(name="position_id")
	private int positionId;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeePass() {
		return employeePass;
	}

	public void setEmployeePass(String employeePass) {
		this.employeePass = employeePass;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public int getInsertEmployeeId() {
		return insertEmployeeId;
	}

	public void setInsertEmployeeId(int insertEmployeeId) {
		this.insertEmployeeId = insertEmployeeId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getUpdateEmployeeId() {
		return updateEmployeeId;
	}

	public void setUpdateEmployeeId(int updateEmployeeId) {
		this.updateEmployeeId = updateEmployeeId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	

}
