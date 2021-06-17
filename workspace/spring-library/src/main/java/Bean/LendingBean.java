package Bean;

import java.sql.Date;

public class LendingBean {
	private int lendingId;
	private Date lendingLendDate;
	private Date lendingReturnDate;
	private String lendingFlg;
	private Date insertDate;
	private int insertEmployeeId;
	private Date updateDate;
	private int updateEmployeeId;
	private int usersId;
	private int booksId;
	private String booksName;
	private String booksAuthor;

	public LendingBean() {
	}

	public LendingBean(int lendingId, Date lendingLendDate, Date lendingReturnDate, Date insertDate,
			int insertEmployeeId, int usersId, int booksId, String booksName, String booksAuthor) {
		this.lendingId = lendingId;
		this.lendingLendDate = lendingLendDate;
		this.lendingReturnDate = lendingReturnDate;
		this.insertDate = insertDate;
		this.insertEmployeeId = insertEmployeeId;
		this.usersId = usersId;
		this.booksId = booksId;
		this.booksName = booksName;
		this.booksAuthor = booksAuthor;
	}

	public int getLendingId() {
		return lendingId;
	}

	public void setLendingId(int lendingId) {
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

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

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
}
