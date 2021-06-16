package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//利用者テーブル
@Entity
@Table(name="users")
public class Users {

	//利用者ID
	@Id
	@Column(name="users_")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int usersId;

	//利用者名
	@Column(name="users_name")
	private String usersName;

	//利用者の住所
	@Column(name="users_address")
	private String usersAddress;

	//利用者の誕生日
	@Column(name="users_birthday")
	private Date usersBirthday;

	//利用者の電話番号
	@Column(name="users_phone")
	private String usersPhone;

	//利用者のメールアドレス
	@Column(name="users_email")
	private String usersEmail;

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

	public Users() {
		super();
	}

	public Users(String usersName, String usersAddress, Date usersBirthday, String usersPhone, String usersEmail, Date insertDate, int insertEmployeeId, Date updateDate, int updateEmployeeId) {
		super();
		this.usersName = usersName;
		this.usersAddress = usersAddress;
		this.usersBirthday = usersBirthday;
		this.usersPhone = usersPhone;
		this.usersEmail = usersEmail;
		this.insertDate = insertDate;
		this.insertEmployeeId = insertEmployeeId;
		this.updateDate = updateDate;
		this.updateEmployeeId = updateEmployeeId;
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

	public String getUsersAddress() {
		return usersAddress;
	}

	public void setUsersAddress(String usersAddress) {
		this.usersAddress = usersAddress;
	}

	public Date getUsersBirthday() {
		return usersBirthday;
	}

	public void setUsersBirthday(Date usersBirthday) {
		this.usersBirthday = usersBirthday;
	}

	public String getUsersPhone() {
		return usersPhone;
	}

	public void setUsersPhone(String usersPhone) {
		this.usersPhone = usersPhone;
	}

	public String getUsersEmail() {
		return usersEmail;
	}

	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
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
