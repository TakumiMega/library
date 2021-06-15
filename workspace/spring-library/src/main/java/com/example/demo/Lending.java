package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lending")
public class Lending {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lending_id")
	private Integer lendingId;

	@Column(name="lending_lend_date")
	private Date lendingLendDate;

	@Column(name="lending_return_date")
	private Date lendingReturnDate;

	@Column(name="lending_flg")
	private String lendingFlg;

	@Column(name="insert_date")
	private Date insertDate;

	@Column(name="insert_employee_id")
	private Integer insertEmployeeId;

	@Column(name="update_date")
	private Date updateDate;

	@Column(name="update_employee_id")
	private Integer updateEmployeeId;

	@Column(name="users_id")
	private Integer usersId;

	@Column(name="books_id")
	private Integer booksId;

	public Lending() {
		super();
	}

	public Lending(Date lendingLendDate,Date lendingReturnDate,String lendingFlg,Date insertDate,
			int insertEmployeeId,Date updateDate,int updateEmployeeId,int usersId,int booksId) {
		super();
		this.lendingLendDate = lendingLendDate;
		this.lendingReturnDate = lendingReturnDate;
		this.lendingFlg = lendingFlg;
		this.insertDate = insertDate;
		this.insertEmployeeId = insertEmployeeId;
		this.updateDate = updateDate;
		this.updateEmployeeId = updateEmployeeId;
		this.usersId = usersId;
		this.booksId = booksId;
	}

	public Integer getLendingId() {
		return lendingId;
	}

	public void setLendingId(Integer lendingId) {
		this.lendingId = lendingId;
	}

	public Date getLendingLendDate() {
		return lendingLendDate;
	}

	public void setLendingLendDate(Date lendingLendDate) {
		this.lendingLendDate = lendingLendDate;
	}

	public Date getLendingReturnDate() {
		return lendingReturnDate;
	}

	public void setLendingReturnDate(Date lendingReturnDate) {
		this.lendingReturnDate = lendingReturnDate;
	}

	public String getLendingFlg() {
		return lendingFlg;
	}

	public void setLendingFlg(String lendingFlg) {
		this.lendingFlg = lendingFlg;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getInsertEmployeeId() {
		return insertEmployeeId;
	}

	public void setInsertEmployeeId(Integer insertEmployeeId) {
		this.insertEmployeeId = insertEmployeeId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateEmployeeId() {
		return updateEmployeeId;
	}

	public void setUpdateEmployeeId(Integer updateEmployeeId) {
		this.updateEmployeeId = updateEmployeeId;
	}

	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public Integer getBooksId() {
		return booksId;
	}

	public void setBooksId(Integer booksId) {
		this.booksId = booksId;
	}

}