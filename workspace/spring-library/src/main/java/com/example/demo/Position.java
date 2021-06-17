package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//役職テーブル
@Entity
@Table(name="position")
public class Position {
	
	//役職ID
	@Id
	@Column(name="position_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int positionId;
	
	//役職名
	@Column(name="position_name")
	private String positionName;
	
	//登録日
	@Column(name="insert_date")
	private Date insertDate;
	
	//登録ユーザーID
	@Column(name="insert_employee_id")
	private int insertEmployeeId;
	
	//更新日
	@Column(name="update_date")
	private Date updateDate;
		
	//更新ユーザーID
	@Column(name="update_employee_id")
	private int updateEmployeeId;

	public Position() {
		
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
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
	
	
	
}
