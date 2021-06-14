package com.example.demo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Books {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "books_id")
	private int booksId;

	@Column(name = "books_name")
	private String booksName;

	@Column(name = "books_author")
	private String booksAuthor;

	@Column(name = "books_stock")
	private int booksStock;

	@Column(name = "books_insert_date")
	private Date booksInsertDate;

	@Column(name = "books_lend")
	private String booksLend;

	@Column(name = "books_remark")
	private String booksRemark;

	@Column(name = "insert_date")
	private Date insertDate;

	@Column(name = "insert_employee_id")
	private int insertEmployeeId;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "update_employee_id")
	private int updateEmployeeId;

	@Column(name = "classification_id")
	private int classificationId;

	public int getBooksId() {
		return booksId;
	}

	public void setBooksId(int booksId) {
		this.booksId = booksId;
	}

	public String getBooksName() {
		return booksName;
	}

	public void setBooksName(String booksName) {
		this.booksName = booksName;
	}

	public String getBooksAuthor() {
		return booksAuthor;
	}

	public void setBooksAuthor(String booksAuthor) {
		this.booksAuthor = booksAuthor;
	}

	public int getBooksStock() {
		return booksStock;
	}

	public void setBooksStock(int booksStock) {
		this.booksStock = booksStock;
	}

	public Date getBooksInsertDate() {
		return booksInsertDate;
	}

	public void setBooksInsertDate(Date booksInsertDate) {
		this.booksInsertDate = booksInsertDate;
	}

	public String getBooksLend() {
		return booksLend;
	}

	public void setBooksLend(String booksLend) {
		this.booksLend = booksLend;
	}

	public String getBooksRemark() {
		return booksRemark;
	}

	public void setBooksRemark(String booksRemark) {
		this.booksRemark = booksRemark;
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

	public int getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}

	Books(){

	}
	Books(String booksName, String booksAuthor, int booksStock, String booksRemark, int classificationId){
		this.booksName=booksName;
		this.booksAuthor=booksAuthor;
		this.booksStock=booksStock;
		this.booksRemark=booksRemark;
		this.classificationId=classificationId;

	}

}
