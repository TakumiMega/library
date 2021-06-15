package Bean;

import java.sql.Date;

public class BooksBean {
	private int booksId;
	private String booksName;
	private String booksAuthor;
	private int booksStock;
	private Date booksInsertDate;
	private String booksLend;
	private String booksRemark;
	private Date insertDate;
	private int insertEmployeeId;
	private Date updateDate;
	private int updateEmployeeId;
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


	public BooksBean() {
	}

	public BooksBean(int booksId, String booksName, String booksAuthor) {
		this.booksId = booksId;
		this.booksName = booksName;
		this.booksAuthor = booksAuthor;
	}
}
